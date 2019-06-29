/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import com.innosar.bean.AddressBean;

public class AddressBeanProcessor extends BeanProcessor {

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

		List<AddressBean> beanList = new LinkedList<>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	@SuppressWarnings("unused")
	private AddressBean getResultObject(ResultSet rs) throws SQLException {
		AddressBean result = new AddressBean();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("SNO".equalsIgnoreCase(columnName)) {
				result.setSno(rs.getInt("SNO"));
			} else if ("ADDRESS1".equalsIgnoreCase(columnName)) {
				result.setAddress1(rs.getString("ADDRESS1"));
			}else if ("ADDRESS2".equalsIgnoreCase(columnName)) {
				result.setAddress2(rs.getString("ADDRESS2"));
			}else if ("ADDRESS3".equalsIgnoreCase(columnName)) {
				result.setAddress3(rs.getString("ADDRESS3"));
			}else if ("ADDRESS4".equalsIgnoreCase(columnName)) {
				result.setAddress4(rs.getString("ADDRESS4"));
			}else if ("ADDRESS5".equalsIgnoreCase(columnName)) {
				result.setAddress5(rs.getString("ADDRESS4"));
			}else if ("GST".equalsIgnoreCase(columnName)) {
				result.setGst(rs.getString("GST"));
			}else if ("WEB".equalsIgnoreCase(columnName)) {
				result.setWeb(rs.getString("WEB"));
			}else if ("EMAIL".equalsIgnoreCase(columnName)) {
				result.setEmail(rs.getString("EMAIL"));
			}else if ("FB".equalsIgnoreCase(columnName)) {
				result.setFb(rs.getString("FB"));
			}else if ("INSTA".equalsIgnoreCase(columnName)) {
				result.setInsta(rs.getString("INSTA"));
			}else if ("MOB".equalsIgnoreCase(columnName)) {
				result.setMob(rs.getString("MOB"));
			}else if ("TWIT".equalsIgnoreCase(columnName)) {
				result.setTwit(rs.getString("TWIT"));
			}

		}

		return result;
	}

}
