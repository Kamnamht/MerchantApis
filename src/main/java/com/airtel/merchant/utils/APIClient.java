package com.airtel.merchant.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class APIClient {

	private static Logger logger = LoggerFactory.getLogger(APIClient.class);
	
	public static String protocol;
	@Value("${BIPUBLISHER_PROTOCOL:http}")
	public void setProtocol(String protocol)	{
		APIClient.protocol = protocol;
	}
	public static String hostname;
	@Value("${BIPUBLISHER_HOSTNAME:10.14.192.21}")
	public void setHostname(String hostname)	{
		APIClient.hostname = hostname;
	}
	public static String port;
	@Value("${BIPUBLISHER_PORT:9704}")
	public void setPort(String port)	{
		APIClient.port = port;
	}
	public static String contextPath;
	@Value("${BIPUBLISHER_CONTEXT_PATH:xmlpserver/services/PublicReportService_v11}")
	public void setContextPath(String contextPath)	{
		APIClient.contextPath = contextPath;
	}

	public static String getSoapResponse(String ftlRequest) throws IOException, TransformerException {
		//protocol+host+port+contextPath
		//"http://10.14.192.21:9704/xmlpserver/services/PublicReportService_v11"
		URL obj = new URL(protocol+"://"+hostname+":"+port+"/"+contextPath);
		logger.info("Calling BI Publiser "+obj);
		logger.info("Request "+ftlRequest);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("SOAPAction", "");
		connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/xml");

		OutputStream os = connection.getOutputStream();

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		StreamSource source = new StreamSource(new StringReader(ftlRequest));
		StreamResult result = new StreamResult(os);
		transformer.transform(source, result);

		os.flush();
		connection.getResponseCode();

		int responseCode = connection.getResponseCode();
		logger.info("Status Code :: "+responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		connection.disconnect();
		String responseString = response.toString();
		logger.info("Response :: "+responseString);
		return responseString;
		
	}
}