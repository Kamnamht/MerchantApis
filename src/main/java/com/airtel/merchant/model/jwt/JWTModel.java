package com.airtel.merchant.model.jwt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "district", "iat", "mobileno", "status", "address", "userType", "parentName", "shopName",
//		"actorName", "channel", "nbf", "actorId", "parentMsisdn", "parentId", "parentNum", "circle", "certificateDate",
//		"exp", "panNumber", "accountId", "tokenExpTime", "jti", "gstNumber", "actorType", "actorCategory" })
public class JWTModel {

	@JsonProperty("district")
	private String district;
	@JsonProperty("iat")
	private Integer iat;
	@JsonProperty("mobileno")
	private String mobileno;
	@JsonProperty("status")
	private String status;
	@JsonProperty("address")
	private String address;
	@JsonProperty("userType")
	private String userType;
	@JsonProperty("parentName")
	private String parentName;
	@JsonProperty("shopName")
	private String shopName;
	@JsonProperty("actorName")
	private String actorName;
	@JsonProperty("channel")
	private String channel;
	@JsonProperty("nbf")
	private Integer nbf;
	@JsonProperty("actorId")
	private String actorId;
	@JsonProperty("parentMsisdn")
	private String parentMsisdn;
	@JsonProperty("parentId")
	private String parentId;
	@JsonProperty("parentNum")
	private String parentNum;
	@JsonProperty("circle")
	private String circle;
	@JsonProperty("certificateDate")
	private String certificateDate;
	@JsonProperty("exp")
	private Integer exp;
	@JsonProperty("panNumber")
	private String panNumber;
	@JsonProperty("accountId")
	private String accountId;
	@JsonProperty("tokenExpTime")
	private String tokenExpTime;
	@JsonProperty("jti")
	private String jti;
	@JsonProperty("gstNumber")
	private String gstNumber;
	@JsonProperty("actorType")
	private String actorType;
	@JsonProperty("actorCategory")
	private String actorCategory;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public JWTModel() {
	}

	/**
	 * 
	 * @param actorCategory
	 * @param accountId
	 * @param certificateDate
	 * @param parentMsisdn
	 * @param status
	 * @param nbf
	 * @param circle
	 * @param panNumber
	 * @param iat
	 * @param mobileno
	 * @param userType
	 * @param parentNum
	 * @param exp
	 * @param parentId
	 * @param actorType
	 * @param address
	 * @param tokenExpTime
	 * @param shopName
	 * @param actorId
	 * @param jti
	 * @param district
	 * @param channel
	 * @param gstNumber
	 * @param actorName
	 * @param parentName
	 */
	public JWTModel(String district, Integer iat, String mobileno, String status, String address, String userType,
			String parentName, String shopName, String actorName, String channel, Integer nbf, String actorId,
			String parentMsisdn, String parentId, String parentNum, String circle, String certificateDate, Integer exp,
			String panNumber, String accountId, String tokenExpTime, String jti, String gstNumber, String actorType,
			String actorCategory) {
		super();
		this.district = district;
		this.iat = iat;
		this.mobileno = mobileno;
		this.status = status;
		this.address = address;
		this.userType = userType;
		this.parentName = parentName;
		this.shopName = shopName;
		this.actorName = actorName;
		this.channel = channel;
		this.nbf = nbf;
		this.actorId = actorId;
		this.parentMsisdn = parentMsisdn;
		this.parentId = parentId;
		this.parentNum = parentNum;
		this.circle = circle;
		this.certificateDate = certificateDate;
		this.exp = exp;
		this.panNumber = panNumber;
		this.accountId = accountId;
		this.tokenExpTime = tokenExpTime;
		this.jti = jti;
		this.gstNumber = gstNumber;
		this.actorType = actorType;
		this.actorCategory = actorCategory;
	}

	@JsonProperty("district")
	public Object getDistrict() {
		return district;
	}

	@JsonProperty("district")
	public void setDistrict(String district) {
		this.district = district;
	}

	@JsonProperty("iat")
	public Integer getIat() {
		return iat;
	}

	@JsonProperty("iat")
	public void setIat(Integer iat) {
		this.iat = iat;
	}

	@JsonProperty("mobileno")
	public String getMobileno() {
		return mobileno;
	}

	@JsonProperty("mobileno")
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty("userType")
	public String getUserType() {
		return userType;
	}

	@JsonProperty("userType")
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@JsonProperty("parentName")
	public String getParentName() {
		return parentName;
	}

	@JsonProperty("parentName")
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@JsonProperty("shopName")
	public String getShopName() {
		return shopName;
	}

	@JsonProperty("shopName")
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@JsonProperty("actorName")
	public String getActorName() {
		return actorName;
	}

	@JsonProperty("actorName")
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	@JsonProperty("channel")
	public String getChannel() {
		return channel;
	}

	@JsonProperty("channel")
	public void setChannel(String channel) {
		this.channel = channel;
	}

	@JsonProperty("nbf")
	public Integer getNbf() {
		return nbf;
	}

	@JsonProperty("nbf")
	public void setNbf(Integer nbf) {
		this.nbf = nbf;
	}

	@JsonProperty("actorId")
	public String getActorId() {
		return actorId;
	}

	@JsonProperty("actorId")
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	@JsonProperty("parentMsisdn")
	public String getParentMsisdn() {
		return parentMsisdn;
	}

	@JsonProperty("parentMsisdn")
	public void setParentMsisdn(String parentMsisdn) {
		this.parentMsisdn = parentMsisdn;
	}

	@JsonProperty("parentId")
	public String getParentId() {
		return parentId;
	}

	@JsonProperty("parentId")
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@JsonProperty("parentNum")
	public String getParentNum() {
		return parentNum;
	}

	@JsonProperty("parentNum")
	public void setParentNum(String parentNum) {
		this.parentNum = parentNum;
	}

	@JsonProperty("circle")
	public String getCircle() {
		return circle;
	}

	@JsonProperty("circle")
	public void setCircle(String circle) {
		this.circle = circle;
	}

	@JsonProperty("certificateDate")
	public String getCertificateDate() {
		return certificateDate;
	}

	@JsonProperty("certificateDate")
	public void setCertificateDate(String certificateDate) {
		this.certificateDate = certificateDate;
	}

	@JsonProperty("exp")
	public Integer getExp() {
		return exp;
	}

	@JsonProperty("exp")
	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@JsonProperty("panNumber")
	public String getPanNumber() {
		return panNumber;
	}

	@JsonProperty("panNumber")
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	@JsonProperty("accountId")
	public String getAccountId() {
		return accountId;
	}

	@JsonProperty("accountId")
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@JsonProperty("tokenExpTime")
	public String getTokenExpTime() {
		return tokenExpTime;
	}

	@JsonProperty("tokenExpTime")
	public void setTokenExpTime(String tokenExpTime) {
		this.tokenExpTime = tokenExpTime;
	}

	@JsonProperty("jti")
	public String getJti() {
		return jti;
	}

	@JsonProperty("jti")
	public void setJti(String jti) {
		this.jti = jti;
	}

	@JsonProperty("gstNumber")
	public String getGstNumber() {
		return gstNumber;
	}

	@JsonProperty("gstNumber")
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	@JsonProperty("actorType")
	public String getActorType() {
		return actorType;
	}

	@JsonProperty("actorType")
	public void setActorType(String actorType) {
		this.actorType = actorType;
	}

	@JsonProperty("actorCategory")
	public String getActorCategory() {
		return actorCategory;
	}

	@JsonProperty("actorCategory")
	public void setActorCategory(String actorCategory) {
		this.actorCategory = actorCategory;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("district", district).append("iat", iat).append("mobileno", mobileno)
				.append("status", status).append("address", address).append("userType", userType)
				.append("parentName", parentName).append("shopName", shopName).append("actorName", actorName)
				.append("channel", channel).append("nbf", nbf).append("actorId", actorId)
				.append("parentMsisdn", parentMsisdn).append("parentId", parentId).append("parentNum", parentNum)
				.append("circle", circle).append("certificateDate", certificateDate).append("exp", exp)
				.append("panNumber", panNumber).append("accountId", accountId).append("tokenExpTime", tokenExpTime)
				.append("jti", jti).append("gstNumber", gstNumber).append("actorType", actorType)
				.append("actorCategory", actorCategory).append("additionalProperties", additionalProperties).toString();
	}

}
