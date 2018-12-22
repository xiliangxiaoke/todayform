package com.today.todayfarm.dom;

public class User {

	//"userId":"20181014150813","phone":"13401167851","fullName":"","orgName":"","orgAddress":"","remark":""

//	private Integer id;
//
//	private String sex;
//	private String company;
//	private String job;
//	private String password;
//	private String username;
//	private String phone;
//	private String address;
//	private String token;
	
	
	
	
	String userId;
	String phone;
	String fullName;
	String orgName;
	String orgAddress;
	String remark;
	String headImgUrl;


	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
