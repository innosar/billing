/*Developed by Rakesh M D & Abhijith T N
 Copyright 2015*/

package com.innosar.dao;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;

public enum DatabaseConnection {

	DB;
	public Connection getConnection() {


		try {

			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Rakesh123");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

	public static QueryRunner getQueryRunner() {
		return new QueryRunner();
	}

}





