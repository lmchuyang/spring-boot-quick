package com.purang.transaction.project;

public class AddressModel {

	private int id;
	private String province;
	private String city;
	private String street;
	private int user_Id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(int i) {
		this.user_Id = i;
	}
	
}
