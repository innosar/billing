package com.innosar.mypackage;



import org.apache.commons.dbutils.DbUtils;
import com.innosar.dao.DatabaseConnection;
import com.innosar.utils.JsfUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;


@ManagedBean(name = "addItem")
@SessionScoped

public class AddItem implements Serializable {

	private static final long serialVersionUID = -8125204286168763912L;



	private int sno;
	private String item;
	private String rate;
	private String created;
	private String modified;


	public int getSno() {
		return sno;
	}



	public void setSno(int sno) {
		this.sno = sno;
	}



	public String getItem() {
		return item;
	}



	public void setItem(String item) {
		this.item = item;
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






	public void action() {

		Connection con = null;

		try {
			con = DatabaseConnection.DB.getConnection();
			String sqlString = "INSERT INTO ADD_ITEM(ITEM, RATE, CREATED, MODIFIED) VALUES (?, ?, NOW(), NOW())";
			DatabaseConnection.getQueryRunner().update(con, sqlString, item, rate);
			JsfUtils.addSuccessMessage("Successful");
		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
	}



}

