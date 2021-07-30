package com.example.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
		String idActual = "";
		while (rs.next()) {
			titleActual = rs.getString(2);
			idActual = rs.getString(1);
		}
		rs.close();
		log.info("Actual title: " + titleActual + " id: " + idActual);
		String titleExpected = "Dopo le esequie";
		Assertions.assertEquals(titleExpected, titleActual);

		String updateQuery = "update LIBTEST set title = ? where ID = ?";
		PreparedStatement ps = conn.prepareStatement(updateQuery);
		ps.setString(2, "2");
		ps.setString(1, "10 Piccoli Indiani");
		ps.executeUpdate();

		ResultSet rsCheck = stat.executeQuery("select ID, TITLE, AUTHOR from LIBTEST");
		String titleUpdatedActual = "";
		while (rsCheck.next()) {
			rsCheck.getString(1);
			titleUpdatedActual = rsCheck.getString(2);
			log.info("row: " + rsCheck.getString(2));
		}
		String titleUpdatedExpected = "10 Piccoli Indiani";
		Assertions.assertEquals(titleUpdatedExpected, titleUpdatedActual);
	}

	@Test
	void deleteQueryWithDriverManager() throws SQLException {
		PreparedStatement prep = conn.prepareStatement("insert into LIBTEST (TITLE, AUTHOR) values (?, ?)");
		prep.setString(1, "Dopo le esequie");
		prep.setString(2, "Agatha Christie");
		prep.addBatch();

		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true);
		
		List<String> beforDeleteList = new ArrayList<String>();
		ResultSet rsBefore = stat.executeQuery("select ID, TITLE, AUTHOR from LIBTEST");
		while (rsBefore.next()) {
			beforDeleteList.add(rsBefore.getString(1) + " " + rsBefore.getString(2) + " " + rsBefore.getString(3));
		}
		log.info(beforDeleteList.toString());

		int rowDeleted = stat.executeUpdate("delete from LIBTEST where ID = 3");
		log.info("Number of row deleted: " + rowDeleted);
		Assertions.assertEquals(1, rowDeleted);

		List<String> afterDeleteList = new ArrayList<String>();
		ResultSet rsAfter = stat.executeQuery("select ID, TITLE, AUTHOR from LIBTEST");
		while (rsAfter.next()) {
			afterDeleteList.add(rsAfter.getString(1) + " " + rsAfter.getString(2) + " " + rsAfter.getString(3));
		}
		log.info(afterDeleteList.toString());
	}

	@BeforeEach
	void dropTableCreated() throws SQLException {
		// stat = conn.createStatement();
		String drop = " TRUNCATE TABLE LIBTEST";
		stat.executeUpdate(drop);
		log.info("Stat: " + stat);
		conn.commit();
		// conn.close();
	}
}
