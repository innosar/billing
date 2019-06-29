/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import org.apache.commons.dbutils.DbUtils;
import com.innosar.bean.UserInfo;
@ManagedBean
@ViewScoped
@RequestScoped
@SessionScoped
public class LoginDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public void validateCredentials(final UserInfo userInfo) {

		Connection conn = DatabaseConnection.DB.getConnection();


		try {
			HttpSession session =
         		(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);


			PreparedStatement pstmt = conn
					.prepareStatement("SELECT ROLE FROM USER_INFO WHERE UPPER(USERNAME)=UPPER(?) AND PASSWORD=MD5(?)");
			pstmt.setString(1, userInfo.getUsername());
			pstmt.setString(2, userInfo.getPassword());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				userInfo.setRole(rs.getInt(1));

		             session.setAttribute("userLogin",userInfo.getUsername());
			}

			DbUtils.closeQuietly(conn, pstmt, rs);


			//System.out.println(userInfo +"already exists");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean changePassword(final String username, final String currPwd,
			final String newPwd) throws Exception {

		Connection conn = DatabaseConnection.DB.getConnection();

		try {
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT COUNT(1) FROM USER_INFO WHERE UPPER(USERNAME)=UPPER(?) AND PASSWORD=MD5(?)");
			pstmt.setString(1, username);
			pstmt.setString(2, currPwd);
			ResultSet rs = pstmt.executeQuery();
			int rowCount = 0;
			while (rs.next()) {
				rowCount = rs.getInt(1);
			}

			if (rowCount == 1) {
				DbUtils.closeQuietly(null, pstmt, rs);
				pstmt = conn
						.prepareStatement("UPDATE USER_INFO SET PASSWORD = MD5(?) WHERE UPPER(USERNAME) = UPPER(?) AND PASSWORD=MD5(?)");
				pstmt.setString(1, newPwd);
				pstmt.setString(2, username);
				pstmt.setString(3, currPwd);
				rowCount = pstmt.executeUpdate();
			} else {
				throw new Exception("Incorrect current password");
			}

			DbUtils.closeQuietly(null, pstmt, null);

			if (rowCount == 1) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn, null, null);
		}

		return false;
	}

}
