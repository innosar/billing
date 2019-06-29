/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import com.innosar.bean.TableBill;

public class TableBillProcessor extends BeanProcessor {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.commons.dbutils.BeanProcessor#toBean(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {

		if (rs.next()) {
			return (T) getResultObject(rs);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.commons.dbutils.BeanProcessor#toBeanList(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type)
			throws SQLException {

		List<TableBill> beanList = new LinkedList<>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	@SuppressWarnings("unused")
	private TableBill getResultObject(ResultSet rs) throws SQLException {
		TableBill result = new TableBill();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("FNO".equalsIgnoreCase(columnName)) {
				result.setFno(rs.getInt("FNO"));
			} else if ("TNAME".equalsIgnoreCase(columnName)) {
				result.setTname(rs.getString("TNAME"));
			}else if ("TAXVAL".equalsIgnoreCase(columnName)) {
				result.setTaxval(rs.getString("TAXVAL"));
			}else if ("CGST".equalsIgnoreCase(columnName)) {
				result.setCgst(rs.getString("CGST"));
			}else if ("SGST".equalsIgnoreCase(columnName)) {
				result.setSgst(rs.getString("SGST"));
			}else if ("DISCP".equalsIgnoreCase(columnName)) {
				result.setDiscp(rs.getString("DISCP"));
			}else if ("DISCA".equalsIgnoreCase(columnName)) {
				result.setDisca(rs.getString("DISCA"));
			}else if ("MODEP".equalsIgnoreCase(columnName)) {
				result.setModep(rs.getString("MODEP"));
			}else if ("TOTALAMT".equalsIgnoreCase(columnName)) {
				result.setTotalamt(rs.getString("TOTALAMT"));
			} else if ("CREATED".equalsIgnoreCase(columnName)) {
				result.setCreated(rs.getString("CREATED"));
			}else if ("MODIFIED".equalsIgnoreCase(columnName)) {
				result.setModified(rs.getString("MODIFIED"));
			}

		}

		return result;
	}

}
