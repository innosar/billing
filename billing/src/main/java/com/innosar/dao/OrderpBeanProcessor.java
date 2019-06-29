/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import com.innosar.bean.OrderpBean;

public class OrderpBeanProcessor extends BeanProcessor {

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

		List<OrderpBean> beanList = new LinkedList<>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	@SuppressWarnings("unused")
	private OrderpBean getResultObject(ResultSet rs) throws SQLException {
		OrderpBean result = new OrderpBean();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("SNO".equalsIgnoreCase(columnName)) {
				result.setSno(rs.getInt("SNO"));
			} else if ("TNAME".equalsIgnoreCase(columnName)) {
				result.setTname(rs.getString("TNAME"));
			} else if ("ITEM".equalsIgnoreCase(columnName)) {
				result.setItem(rs.getString("ITEM"));
			} else if ("RATE".equalsIgnoreCase(columnName)) {
				result.setRate(rs.getString("RATE"));
			}  else if ("QTY".equalsIgnoreCase(columnName)) {
				result.setQty(rs.getString("QTY"));
			}else if ("AMT".equalsIgnoreCase(columnName)) {
				result.setAmt(rs.getString("AMT"));
			}else if ("CREATED".equalsIgnoreCase(columnName)) {
				result.setCreated(rs.getString("CREATED"));
			}else if ("MODIFIED".equalsIgnoreCase(columnName)) {
				result.setModified(rs.getString("MODIFIED"));
			}else if ("INVOICE".equalsIgnoreCase(columnName)) {
				result.setInvoice(rs.getString("INVOICE"));
			}else if ("STA".equalsIgnoreCase(columnName)) {
				result.setSta(rs.getString("STA"));
			}

		}

		return result;
	}

}
