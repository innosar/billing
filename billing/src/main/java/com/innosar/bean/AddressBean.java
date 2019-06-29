package com.innosar.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;



public class AddressBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8125204286168763912L;

    private int sno;
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



	public int getSno() {
		return sno;
	}



	public void setSno(int sno) {
		this.sno = sno;
	}





	public AddressBean() {
		super();

	}



    public AddressBean mask(Map<String, Object> filters) {

		for (Entry<String, Object> entry : filters.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public AddressBean mask(String pattern, Object object) {
		if (pattern == null || object == null)
			return this;

		if ("sno".equals(pattern)) {
			setSno((int) object);
		}else if ("address1".equals(pattern)) {
			setAddress1((String) object);
		}else if ("address2".equals(pattern)) {
			setAddress2((String) object);
		}else if ("address3".equals(pattern)) {
			setAddress3((String) object);
		}else if ("address4".equals(pattern)) {
			setAddress4((String) object);
		}else if ("address5".equals(pattern)) {
			setAddress5((String) object);
		}else if ("gst".equals(pattern)) {
			setGst((String) object);
		}else if ("web".equals(pattern)) {
			setWeb((String) object);
		}else if ("fb".equals(pattern)) {
			setFb((String) object);
		}else if ("insta".equals(pattern)) {
			setInsta((String) object);
		}else if ("mob".equals(pattern)) {
			setMob((String) object);
		}else if ("twit".equals(pattern)) {
			setTwit((String) object);
		}

		return this;
	}


}
