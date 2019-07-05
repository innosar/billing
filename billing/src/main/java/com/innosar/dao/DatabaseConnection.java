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

			return DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

	public static QueryRunner getQueryRunner() {
		return new QueryRunner();
	}

}





