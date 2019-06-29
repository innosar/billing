/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.dbutils.DbUtils;
import com.innosar.dao.DatabaseConnection;
import com.innosar.dao.LoginDao;
import com.innosar.utils.JsfUtils;

@ManagedBean(name = "addressController")
@RequestScoped
public class AddressControler {

	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String address5;
    private String gst;
    private String email;
    private String mob;
    private String web;
    private String fb;
    private String insta;
    private String twit;






	public String getTwit() {
		return twit;
	}




	public void setTwit(String twit) {
		this.twit = twit;
	}




	public String getAddress5() {
		return address5;
	}




	public void setAddress5(String address5) {
		this.address5 = address5;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getMob() {
		return mob;
	}




	public void setMob(String mob) {
		this.mob = mob;
	}




	public String getWeb() {
		return web;
	}




	public void setWeb(String web) {
		this.web = web;
	}




	public String getFb() {
		return fb;
	}




	public void setFb(String fb) {
		this.fb = fb;
	}




	public String getInsta() {
		return insta;
	}




	public void setInsta(String insta) {
		this.insta = insta;
	}




	public String getAddress2() {
		return address2;
	}




	public void setAddress2(String address2) {
		this.address2 = address2;
	}




	public String getAddress3() {
		return address3;
	}




	public void setAddress3(String address3) {
		this.address3 = address3;
	}




	public String getAddress4() {
		return address4;
	}




	public void setAddress4(String address4) {
		this.address4 = address4;
	}




	public String getGst() {
		return gst;
	}




	public void setGst(String gst) {
		this.gst = gst;
	}




	public String getAddress1() {
		return address1;
	}




	public void setAddress1(String address1) {
		this.address1 = address1;
	}




	public void submit() {

		Connection con = null;

		try {
			con = DatabaseConnection.DB.getConnection();
			String sqlString = "INSERT INTO ADDRESS_TABLE(ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,ADDRESS5,GST,MOB,WEB,EMAIL,FB,INSTA,TWIT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			DatabaseConnection.getQueryRunner().update(con, sqlString, address1,address2,address3,address4,address5,gst,mob,web,email,fb,insta,twit);
			JsfUtils.addSuccessMessage("Successful");
		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
	}




}
