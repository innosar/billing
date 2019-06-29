package com.innosar.mypackage;



import org.apache.commons.dbutils.DbUtils;
import org.primefaces.context.RequestContext;
import com.innosar.dao.DatabaseConnection;
import com.innosar.utils.JsfUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;


@ManagedBean(name = "addTable")
@SessionScoped
@ViewScoped

public class AddTable implements Serializable {

	private static final long serialVersionUID = -8125204286168763912L;



	private int sno;
	private String tname;
	private String sta;
	private String created;
	private String modified;


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



	public String getSta() {
		return sta;
	}



	public void setSta(String sta) {
		this.sta = sta;
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
			String sqlString = "INSERT INTO ADD_TABLE(TNAME, STA, CREATED, MODIFIED) VALUES (?, 'ACTIVE', NOW(), NOW())";
			DatabaseConnection.getQueryRunner().update(con, sqlString, tname);
			JsfUtils.addSuccessMessage("Successful");

		} catch (SQLException ex) {
			JsfUtils.addErrorMessage("Error!" + ex.getMessage());
			System.out.println(ex.getMessage());
		} finally {
			DbUtils.closeQuietly(con);
		}
	}



}

