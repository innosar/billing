package com.innosar.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;



public class OrderBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8125204286168763912L;

    private int sno;
	private String tname;
	private String item;
	private String qty;
	private String rate;
	private String amt;
	private String sta;
	private String created;
	private String modified;



    public String getSta() {
		return sta;
	}



	public void setSta(String sta) {
		this.sta = sta;
	}



	public int getSno() {
		return sno;
	}



	public void setSno(int sno) {
		this.sno = sno;
	}



	public String getTname() {
		return tname;
	}



	public void setTname(String tname) {
		this.tname = tname;
	}



	public String getItem() {
		return item;
	}



	public void setItem(String item) {
		this.item = item;
	}



	public String getQty() {
		return qty;
	}



	public void setQty(String qty) {
		this.qty = qty;
	}



	public String getRate() {
		return rate;
	}



	public void setRate(String rate) {
		this.rate = rate;
	}



	public String getAmt() {
		return amt;
	}



	public void setAmt(String amt) {
		this.amt = amt;
	}



	public String getCreated() {
		return created;
	}



	public void setCreated(String created) {
		this.created = created;
	}



	public String getModified() {
		return modified;
	}



	public void setModified(String modified) {
		this.modified = modified;
	}



	public OrderBean() {
		super();

	}



    public OrderBean mask(Map<String, Object> filters) {

		for (Entry<String, Object> entry : filters.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public OrderBean mask(String pattern, Object object) {
		if (pattern == null || object == null)
			return this;

		if ("sno".equals(pattern)) {
			setSno((int) object);
		} else if ("tname".equals(pattern)) {
			setTname((String) object);
		} else if ("item".equals(pattern)) {
			setItem((String) object);
		} else if ("qty".equals(pattern)) {
			setQty((String) object);
		} else if ("rate".equals(pattern)) {
			setRate((String) object);
		} else if ("amt".equals(pattern)) {
			setAmt((String) object);
		} else if ("created".equals(pattern)) {
			setCreated((String) object);
		}else if ("modified".equals(pattern)) {
			setModified((String) object);
		}else if ("sta".equals(pattern)) {
			setSta((String) object);
		}

		return this;
	}


}
