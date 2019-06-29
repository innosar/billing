package com.innosar.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;



public class ItemBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8125204286168763912L;

    private int sno;
    private int rate;
	private String item;

	private String created;
	private String modified;


	public int getSno() {
		return sno;
	}



	public void setSno(int sno) {
		this.sno = sno;
	}




	public int getRate() {
		return rate;
	}



	public void setRate(int rate) {
		this.rate = rate;
	}



	public String getItem() {
		return item;
	}



	public void setItem(String item) {
		this.item = item;
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



	public ItemBean() {
		super();

	}



    public ItemBean mask(Map<String, Object> filters) {

		for (Entry<String, Object> entry : filters.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public ItemBean mask(String pattern, Object object) {
		if (pattern == null || object == null)
			return this;

		if ("sno".equals(pattern)) {
			setSno((int) object);
		}else if ("item".equals(pattern)) {
			setItem((String) object);
		}else if ("rate".equals(pattern)) {
			setRate((int) object);
		} else if ("created".equals(pattern)) {
			setCreated((String) object);
		}else if ("modified".equals(pattern)) {
			setModified((String) object);
		}

		return this;
	}


}
