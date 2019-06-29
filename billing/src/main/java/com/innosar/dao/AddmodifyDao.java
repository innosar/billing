/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import com.innosar.bean.Addmodify;

public class AddmodifyDao {


	public boolean insertPersonalDetails(List<Addmodify> addmodifyList) {

		Connection conn = null;

		boolean isSuccessful = false;

		try {

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(false);

		//	Object[][] params = getParamForInsertAddmodifyInfo(addmodifyList);

			QueryRunner qr = new QueryRunner();

		//	qr.batch(conn, Queries.INSERT_USER_INFO, params);
		//	qr.update(conn, Queries.PROMOTE_ELIGIBLE1_RESULTS);

			isSuccessful = true;

			conn.commit();

		} catch (SQLException e) {
			try {
				System.out.println("Rolled back : " + e.getMessage());
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return isSuccessful;
	}

	public void search(QueryData<Addmodify> qd) {

		Connection conn = null;

		int first = qd.getFirst();
		int pagesize = qd.getPageSize();
		Addmodify filter = qd.getFilter();
		String sortOrder = qd.getSortOrder();
		String sortField = qd.getSortField();

		try {

			String countQuery = "SELECT COUNT(*) FROM USER_INFO WHERE 1 =1";
			String resultQuery = "SELECT * FROM USER_INFO WHERE 1 = 1 ";
			String whereClause = buildWhereClause(filter);
			Object[] params = getParams(filter);

			conn = DatabaseConnection.DB.getConnection();
			QueryRunner qr = new QueryRunner();

			PreparedStatement pstmt = conn.prepareStatement(countQuery
					+ whereClause);

			qr.fillStatement(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}

			DbUtils.closeQuietly(null, pstmt, rs);

			qd.setTotalResultCount(count);

			if (count > 0) {
				StringBuilder sortOrderBuilder = new StringBuilder();
				if ("auser".equals(sortField)) {
					sortOrderBuilder.append(" ORDER BY USERNAME ").append(
							sortOrder);
				} else {
					sortOrderBuilder.append(" ORDER BY ROLE ")
							.append(sortOrder);
				}

				sortOrderBuilder.append(" LIMIT ").append(first).append(",")
						.append(pagesize);

				List<Addmodify> resultList = qr.query(conn, resultQuery
						+ whereClause + sortOrderBuilder.toString(),
						new BeanListHandler<Addmodify>(Addmodify.class,
								new BasicRowProcessor(
										new AddmodifyBeanProcessor())), params);

				qd.setData(resultList);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	private String buildWhereClause(Addmodify addmodify) {
		StringBuilder sb = new StringBuilder(100);
		if (StringUtils.isNotBlank(addmodify.getAuser())) {
			sb.append(" AND USERNAME LIKE ? ");
		}

		if (StringUtils.isNotBlank(addmodify.getArole())) {
			sb.append(" AND ROLE like ? ");
		}


		return sb.toString();
	}

	private Object[] getParams(Addmodify addmodify) throws SQLException {

		List<String> params = new ArrayList<String>();

		if (StringUtils.isNotBlank(addmodify.getAuser())) {
			params.add("%" + addmodify.getAuser() + "%");
		}

		if (StringUtils.isNotBlank(addmodify.getArole())) {
			params.add("%" + addmodify.getArole() + "%");
		}



		if (params.size() == 0) {
			return null;
		} else {
			return params.toArray();
		}

	}

	/**
	 * @param resultList
	 */
	/*private Object[][] getParamForInsertAddmodifyInfo(List<Addmodify> addmodifyList) {
		Object[][] params = new Object[addmodifyList.size()][5];
		// REG_NO, NAME,FATHER,MOTHER,GENDER,CATEGORY
		for (int i = 0; i < addmodifyList.size(); i++) {
			Addmodify addmodify = addmodifyList.get(i);
			int j = 0;
			params[i][j++] = addmodify.getAuser();
			params[i][j++] = addmodify.getArole();
			params[i][j++] = addmodify.getYr();
			params[i][j++] = addmodify.getScode();
			params[i][j++] = addmodify.getSname();

		}

		return params;
	}
*/
	public boolean updateAddmodify(Addmodify addmodify) {

		Connection conn = null;

		try {
			if(!addmodify.getArole().equals("2") && !addmodify.getArole().equals("4")){
			String updateQuery = "REPLACE INTO USER_INFO (USERNAME,ROLE,PASSWORD) VALUES(?,?,?)";

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(true);

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			int i = 1;
			pstmt.setString(i++, addmodify.getAuser());
			pstmt.setString(i++, addmodify.getArole());
			pstmt.setString(i++, addmodify.getApass());


			int rowCount = pstmt.executeUpdate();

			return rowCount == 1;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return false;

	}

	public boolean deleteAddmodify(Addmodify addmodify) {

		Connection conn = null;

		try {

			String updateQuery = "DELETE FROM USER_INFO WHERE  USERNAME= ?  AND ROLE!='2' AND ROLE!='4'";

			conn = DatabaseConnection.DB.getConnection();

			conn.setAutoCommit(true);

			PreparedStatement pstmt = conn.prepareStatement(updateQuery);
			int i = 1;
			pstmt.setString(i++, addmodify.getAuser());

			int rowCount = pstmt.executeUpdate();

			return rowCount == 1;

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}

		return false;

	}
}
