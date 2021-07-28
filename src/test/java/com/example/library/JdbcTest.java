package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

class JdbcTest {

	private static final Logger log = Logger.getLogger(JdbcTest.class.getName());
	private static Connection conn;
	private static Statement stat;

	@BeforeAll
	private static void setUpDerbyDatabase() throws SQLException, ClassNotFoundException {
		log.info("BeforeAll");
		// Creating testDB database
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		conn = DriverManager.getConnection("jdbc:derby:memory:TestingDB;create=true");
		log.info("Building JPA EntityManager for unit tests");
		log.info("Conn: " + conn);
		stat = conn.createStatement();
		String schema = "CREATE TABLE LIBTEST ("
				+ " ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ " TITLE VARCHAR(255) , AUTHOR VARCHAR(255) )";
		stat.executeUpdate(schema);
		log.info("Stat: " + stat);
		// super.setUp();
		// Creating Entity Manager
		// emFactory = Persistence.createEntityManagerFactory("TestingDB");
		// em = emFactory.createEntityManager();
	}

	@Test
	void testInsertQueryWithDriverManager() throws SQLException {
		PreparedStatement prep = conn.prepareStatement("insert into LIBTEST (TITLE, AUTHOR) values (?, ?)");
		prep.setString(1, "Il mago del nilo");
		prep.setString(2, "Christian Jacq");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(false);

		// Data retrieve
		ResultSet rs = stat.executeQuery("select ID, TITLE, AUTHOR from LIBTEST");

		Map<String, List<String>> actualBookMap = new HashMap<String, List<String>>();
		while (rs.next()) {
			List<String> bookCol = new ArrayList<String>();
			bookCol.add(rs.getString(2));
			bookCol.add(rs.getString(3));

			actualBookMap.put(rs.getString(1), bookCol);
		}
		log.info("actualBookMap: " + actualBookMap.toString());
		log.info("End");

		String expectedBookMap = "{1=[Il mago del nilo, Christian Jacq]}";
		Assertions.assertEquals(expectedBookMap, actualBookMap.toString());
		rs.close();
		/*
		 * DataSource dataSource = new
		 * EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY)
		 * .addScript("classpath:schema.sql") .addScript("classpath:data.sql") .build();
		 */
	}

	@Test
	void testUpdateQueryWithDriverManager() throws SQLException {
		PreparedStatement prep = conn.prepareStatement("insert into LIBTEST (TITLE, AUTHOR) values (?, ?)");
		prep.setString(1, "Dopo le esequie");
		prep.setString(2, "Agatha Christie");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		ResultSet rs = stat.executeQuery("select ID, TITLE, AUTHOR from LIBTEST");

		// Map<String, List<String>> actualBookMap = new HashMap<String,
		// List<String>>();
		String titleActual = "";
		while (rs.next()) {
			titleActual += rs.getString(2);
		}
		rs.close();
		log.info("Actual: " + titleActual);
		String titleExpected = "Dopo le esequie";
		// Assertions.assertEquals(titleExpected, titleActual);

	}

}
