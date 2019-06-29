package com.innosar.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.apache.commons.dbutils.QueryRunner;

import com.innosar.dao.DatabaseConnection;
import com.innosar.utils.JsfUtils;

@ManagedBean(name = "addmodify")
@ViewScoped
@SessionScoped
public class Addmodify implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4122612149984056928L;

	private String arole;

	private String auser;

	private String apass;


	public Addmodify() {
		super();
	}

	public Addmodify(String auser, String arole,
	                 String apass) {
		super();
		this.auser = auser;
		this.apass = apass;
		this.arole = arole;

	}

	/**
	 * @return the auser
	 */
	public String getAuser() {
		return auser;
	}

	/**
	 * @param auser the auser to set
	 */
	public void setAuser(String auser) {
		this.auser = auser;
	}

	/**
	 * @return the apass
	 */
	public String getApass() {
		return apass;
	}

	/**
	 * @param apass the apass to set
	 */
	public void setApass(String apass) {
		this.apass = apass;
	}


	/**
	 * @return the arole
	 */
	public String getArole() {
		return arole;
	}

	/**
	 * @param arole the arole to set
	 */
	public void setArole(String arole) {
		this.arole = arole;
	}


	public Addmodify mask(Map<String, Object> filter) {

		for (Entry<String, Object> entry : filter.entrySet()) {
			mask(entry.getKey(), (String) entry.getValue());
		}

		return this;
	}

	public Addmodify mask(String pattern, String value) {
		if (pattern == null || value == null)
			return this;

		if ("auser".equals(pattern)) {
			setAuser(value);
		} else if ("arole".equals(pattern)) {
			setArole(value);
		}

		return this;
	}


	public void action() {

		try(Connection con = DatabaseConnection.DB.getConnection()) {
			if (!getArole().equals("2") && !getArole().equals("4") && !Objects.equals(auser, "ADMIN")) {
				String createUserStatement = "INSERT INTO USER_INFO(USERNAME, PASSWORD, ROLE) VALUES (?, MD5(?), ?)";
				new QueryRunner().update(con, createUserStatement, auser, auser, arole);
				JsfUtils.addSuccessMessage("New User added Successfully");
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
