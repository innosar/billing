package com.innosar.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "tablebill")
@ViewScoped
@RequestScoped
public class TableBill implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8125204286168763912L;

    private int fno;
	private String tname;
	private String taxval;
	private String cgst;
	private String sgst;
	private String discp;
	private String disca;
	private String modep;
	private String totalamt;
	private String created;
	private String modified;

	private static String taxvalsum;
	private static String cgstsum;
	private static String sgstsum;
	private static String discasum;
	private static String totalamtsum;






	public String getModep() {
		return modep;
	}



	public void setModep(String modep) {
		this.modep = modep;
	}



	public String getDiscp() {
		return discp;
	}



	public void setDiscp(String discp) {
		this.discp = discp;
	}



	public String getDisca() {
		return disca;
	}



	public void setDisca(String disca) {
		this.disca = disca;
	}



	public String getDiscasum() {
		return discasum;
	}



	public void setDiscasum(String discasum) {
		this.discasum = discasum;
	}



	public String getTaxvalsum() {
		return taxvalsum;
	}



	public void setTaxvalsum(String taxvalsum) {
		this.taxvalsum = taxvalsum;
	}



	public String getCgstsum() {
		return cgstsum;
	}



	public void setCgstsum(String cgstsum) {
		this.cgstsum = cgstsum;
	}



	public String getSgstsum() {
		return sgstsum;
	}



	public void setSgstsum(String sgstsum) {
		this.sgstsum = sgstsum;
	}



	public String getTotalamtsum() {
		return totalamtsum;
	}



	public void setTotalamtsum(String totalamtsum) {
		this.totalamtsum = totalamtsum;
	}



	public int getFno() {
		return fno;
	}



	public void setFno(int fno) {
		this.fno = fno;
	}



	public String getTaxval() {
		return taxval;
	}



	public void setTaxval(String taxval) {
		this.taxval = taxval;
	}



	public String getCgst() {
		return cgst;
	}



	public void setCgst(String cgst) {
		this.cgst = cgst;
	}



	public String getSgst() {
		return sgst;
	}



	public void setSgst(String sgst) {
		this.sgst = sgst;
	}



	public String getTotalamt() {
		return totalamt;
	}



	public void setTotalamt(String totalamt) {
		this.totalamt = totalamt;
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



	public TableBill() {
		super();

	}



    public TableBill mask(Map<String, Object> filters) {

		for (Entry<String, Object> entry : filters.entrySet()) {
			mask(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public TableBill mask(String pattern, Object object) {
		if (pattern == null || object == null)
			return this;

		if ("fno".equals(pattern)) {
			setFno((int) object);
		} else if ("tname".equals(pattern)) {
			setTname((String) object);
		}else if ("taxval".equals(pattern)) {
			setTaxval((String) object);
		}else if ("cgst".equals(pattern)) {
			setCgst((String) object);
		}else if ("sgst".equals(pattern)) {
			setSgst((String) object);
		}else if ("discp".equals(pattern)) {
			setDiscp((String) object);
		}else if ("disca".equals(pattern)) {
			setDisca((String) object);
		}else if ("totalamt".equals(pattern)) {
			setTotalamt((String) object);
		} else if ("created".equals(pattern)) {
			setCreated((String) object);
		}else if ("modified".equals(pattern)) {
			setModified((String) object);
		}

		return this;
	}


}
