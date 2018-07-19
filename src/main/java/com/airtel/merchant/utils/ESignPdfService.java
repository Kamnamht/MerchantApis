/*
 * 
 */
package com.airtel.merchant.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.safenetinc.luna.LunaAPI;
import com.safenetinc.luna.LunaSlotManager;
import com.safenetinc.luna.LunaTokenObject;
import com.safenetinc.luna.provider.key.LunaKey;

@Service
public class ESignPdfService {

	private static Logger logger = LoggerFactory.getLogger(ESignPdfService.class);
	public static LunaSlotManager hsmConnection;// = LunaSlotManager.getInstance();
	public static String PartitionName = "INVOICE-SIGNING";
	public static String encPartitionPassword = "u2SLB/lL4TA8Y5/a8WMGl6N0uY3vBRi3";
	public static String CertLabel = "signing--cert";
	public static String lunaLibraryPath;

	@Value("${LUNA_LIBRARY_PATH:}")
	public void setLunaLibraryPath(String lunaLibraryPath) {
		ESignPdfService.lunaLibraryPath = lunaLibraryPath;
	}

	public static void generateESignPdf(String sourceFile, String signedFile) {
		try {
			System.load(lunaLibraryPath + "libLunaAPI.so");
			if (hsmConnection == null) {
				hsmConnection = LunaSlotManager.getInstance();
			}
			final PrivateKey priv = getPrivateKeyLabel();
			final Certificate[] chain = getcertificatechain();

			final File unsignedFile = new File(sourceFile);
			final PdfReader reader = new PdfReader(unsignedFile.getAbsolutePath());
			final File signed = new File(signedFile);
			final FileOutputStream fout = new FileOutputStream(signed);
			final PdfStamper stamper = PdfStamper.createSignature(reader, fout, '\0', null, true);
			final PdfSignatureAppearance sap = stamper.getSignatureAppearance();

			sap.setLocation("India");
			Calendar cal = Calendar.getInstance();
			final Date date = new Date();
			cal.setTime(date);
			sap.setSignDate(cal);
			final int m = stamper.getReader().getNumberOfPages();
			sap.setVisibleSignature(new Rectangle(400, 100, 200, 200), m, "signature");
			sap.setCrypto(priv, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
			final PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, new PdfName("adbe.pkcs7.detached"));
			dic.setReason(sap.getReason());
			dic.setLocation(sap.getLocation());
			dic.setContact(sap.getContact());
			dic.setDate(new PdfDate(sap.getSignDate()));
			sap.setCryptoDictionary(dic);

			final int contentEstimated = 15000;
			final HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(contentEstimated * 2 + 2));
			sap.preClose(exc);

			final PdfPKCS7 sgn = new PdfPKCS7(priv, chain, null, "SHA1", null, false);
			final InputStream data = sap.getRangeStream();
			final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			final byte buf[] = new byte[8192];
			int n;
			while ((n = data.read(buf)) > 0) {
				messageDigest.update(buf, 0, n);
			}
			final byte hash[] = messageDigest.digest();
			cal = Calendar.getInstance();

			final byte sh[] = sgn.getAuthenticatedAttributeBytes(hash, cal, null);
			sgn.update(sh, 0, sh.length);

			final byte[] encodedSig = sgn.getEncodedPKCS7(hash, cal, null, null);

			if (contentEstimated + 2 < encodedSig.length) {
				throw new Exception();
			}

			final byte[] paddedSig = new byte[contentEstimated];
			System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);

			final PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
			sap.close(dic2);
			logger.info("File Generated with Digital Signature : " + signedFile);
		} catch (final Exception e) {
			logger.error("Error while signing GST invoice file " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static PrivateKey getPrivateKeyLabel() throws Exception {

		if (Security.getProvider("LunaProvider") == null) {
			Security.insertProviderAt(new com.safenetinc.luna.provider.LunaProvider(), 2);
		}

		if (!hsmConnection.isLoggedIn()) {
			hsmConnectionLogin();
		}
		final KeyStore ks = KeyStore.getInstance("Luna");
		ks.load(null, null);
		final RSAPublicKey pub = (RSAPublicKey) ks.getCertificate(CertLabel).getPublicKey();
		final BigInteger modulus = pub.getModulus();
		int flag = 1;
		String keyStoreObj = "";
		LunaTokenObject privkey = null;
		final Enumeration<String> aliases = ks.aliases();
		while (aliases.hasMoreElements() && flag != 0) {
			keyStoreObj = aliases.nextElement();
			if (ks.isKeyEntry(keyStoreObj)) {
				privkey = LunaTokenObject.LocateKeyByAlias(keyStoreObj);
				if (privkey.GetSmallAttribute(LunaAPI.CKA_CLASS) == 3) {
					final byte[] mudulus = privkey.GetLargeAttribute(LunaAPI.CKA_MODULUS);
					final BigInteger compare = LunaKey.AttributeToBigInteger(mudulus);
					flag = modulus.compareTo(compare);
				}
			}
		}

		return (PrivateKey) LunaKey.LocateKeyByAlias(keyStoreObj);

	}

	private static void refreshHsmConnection() throws Exception {
		try {
			resolveLunaSlotManagerInstance();
			hsmConnectionLogin();
		} catch (final Throwable t) {
			throw new Exception(
					"Unable to login to the Hardware Storage Module (HSM). E-signing can't be completed without access to a certificate",
					t);
		}
	}

	private static void hsmConnectionLogin() {
		if (!hsmConnection.isLoggedIn()) {
			final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword("root");
			encryptor.setAlgorithm("PBEWithMD5AndDES");
			final String partitionPassword = encryptor.decrypt(encPartitionPassword);
			hsmConnection.login(PartitionName, partitionPassword);
		}

	}

	private static void resolveLunaSlotManagerInstance() throws Exception {
		if (hsmConnection == null) {
			if (hsmConnection.isTokenPresent(hsmConnection.findSlotFromLabel(PartitionName))) {
				hsmConnection = LunaSlotManager.getInstance();
				hsmConnection.reinitialize();

			}
		} else {
			throw new Exception("LunaSlotManager did not return an instance.");
		}
	}

	private static Certificate[] getcertificatechain() throws Exception {
		try {

			if (Security.getProvider("LunaProvider") == null) {
				Security.insertProviderAt(new com.safenetinc.luna.provider.LunaProvider(), 2);
			}
			if (!hsmConnection.isLoggedIn()) {
				refreshHsmConnection();
			}
			final KeyStore ks = KeyStore.getInstance("Luna");
			ks.load(null, null);

			final Certificate[] chain = new Certificate[1];
			chain[0] = ks.getCertificate(CertLabel);
			// hsmConnection.logout();
			return chain;
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new Exception(
					"Unable to retrieve resources from the Hardware Storage Module (HSM). E-signing can't be completed without a private key and certificate chain",
					t);
		}
	}
}
