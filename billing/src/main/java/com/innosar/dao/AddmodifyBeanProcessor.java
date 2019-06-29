/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import com.innosar.bean.Addmodify;

public class AddmodifyBeanProcessor extends BeanProcessor {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.commons.dbutils.BeanProcessor#toBean(java.sql.ResultSet,
	 * java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {

		while (rs.next()) {
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

		List<Addmodify> beanList = new LinkedList<Addmodify>();

		while (rs.next()) {
			beanList.add(getResultObject(rs));
		}

		return (List<T>) beanList;

	}

	private Addmodify getResultObject(ResultSet rs) throws SQLException {
		Addmodify addmodify = new Addmodify();

		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = rsmd.getColumnName(i);
			if ("USERNAME".equalsIgnoreCase(columnName)) {
				addmodify.setAuser(rs.getString(columnName));
			} else if ("ROLE".equalsIgnoreCase(columnName)) {
				addmodify.setArole(rs.getString(columnName));
			}

		}

		return addmodify;
	}

}
