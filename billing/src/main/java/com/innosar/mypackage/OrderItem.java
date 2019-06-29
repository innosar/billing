package com.innosar.mypackage;



import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import com.innosar.dao.DatabaseConnection;
import static com.innosar.dao.DatabaseConnection.*;
import com.innosar.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;




import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "orderItem")
@ViewScoped

public class OrderItem implements Serializable {

	private static final long serialVersionUID = -8125204286168763912L;



	private int sno;
	private String tname;
	private String item;
	private String qty;
	private String rate;
	private String amt;
	private String created;
	private String modified;
	 private List<String> tnames = new ArrayList<>();
	 private List<String> items = new ArrayList<>();



	 public List<String> getTnames() {

	        return tnames;
	    }

	    public String getTname() {
	        return tname;
	    }

	    public void setTname(String tnames) {
	        tname = tnames;
	    }






	public String getQty() {
		return qty;
	}



	public void setQty(String qty) {
		this.qty = qty;
	}



	public String getAmt() {
		return amt;
	}



	public void setAmt(String amt) {
		this.amt = amt;
	}


	public int getSno() {
		return sno;
	}



	public void setSno(int sno) {
		this.sno = sno;
	}



	 public List<String> getItems() {

	        return items;
	    }

	    public String getItem() {
	        return item;
	    }

	    public void setItem(String items) {
	        item = items;
	    }



	public String getRate() {
		return rate;
	}



	public void setRate(String rate) {
		this.rate = rate;
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


	@PostConstruct
	public void init() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			tnames.clear();
			items.clear();
			con = DB.getConnection();




	               String distinctInst = "SELECT DISTINCT TNAME FROM ADD_TABLE WHERE STA='ACTIVE' AND TNAME!='all'";
	               tnames = getQueryRunner().query(con, distinctInst, new ColumnListHandler<String>(1));



	               String distinctFileType = "SELECT ITEM FROM ADD_ITEM WHERE ITEM!='all'";
	               items = getQueryRunner().query(con, distinctFileType, new ColumnListHandler<String>(1));


		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con, st, rs);
		}
	}



	public void action() {

		Connection con = null;

		try {
			con = DatabaseConnection.DB.getConnection();
			String sqlString1 = "INSERT INTO ORDER_ITEM(TNAME,ITEM,QTY,RATE,AMT,STA,INVOICE,CREATED, MODIFIED) VALUES (?,?,?,'','','UNPAID','val',NOW(), NOW())";
			String sqlString2 = "UPDATE ORDER_ITEM INNER JOIN ADD_ITEM ON ADD_ITEM.ITEM=ORDER_ITEM.ITEM SET ORDER_ITEM.RATE=ADD_ITEM.RATE";
			String sqlString3 = "UPDATE ORDER_ITEM  SET AMT=RATE*QTY WHERE STA='UNPAID'";
			DatabaseConnection.getQueryRunner().update(con, sqlString1, tname,item,qty);
			DatabaseConnection.getQueryRunner().update(con, sqlString2);
			DatabaseConnection.getQueryRunner().update(con, sqlString3);
			JsfUtils.addSuccessMessage("Successful");
		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
	}



}

