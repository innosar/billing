package com.innosar.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;



public class TableBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8125204286168763912L;

    private int sno;
	private String tname;
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



	public TableBean() {
		super();

	}



    public TableBean mask(Map<String, Object> filters) {

		for (Entry<String, Object> entry : filters.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public TableBean mask(String pattern, Object object) {
		if (pattern == null || object == null)
			return this;

		if ("sno".equals(pattern)) {
			setSno((int) object);
		} else if ("tname".equals(pattern)) {
			setTname((String) object);
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
