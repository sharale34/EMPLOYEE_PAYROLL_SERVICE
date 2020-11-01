package com.bridgelabz.employeepayroll;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class EmployeePayrollJDBC {

	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "Sourabhharale@143";
		Connection connection;
		try {
			//forName method contains static block which creates the Driver class object and register automatically.
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find driver in classpath!", e);
		}
		listDrivers();
		try {
			System.out.println("Connecting to database: " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful!!!!!" + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void listDrivers() {
		//Generates series of elements, one at a time
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
	}
}
