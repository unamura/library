package com.example.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	private final JdbcTemplate jdbcTemplate;

	public TestService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<BookDto> queryFromDatabase() {

		return jdbcTemplate.query("select ID,TITLE,AUTHOR from LIBTEST", new RowMapper<BookDto>() {

			@Override
			public BookDto mapRow(ResultSet resultSet, int i) throws SQLException {
				BookDto bookDto = new BookDto();
				bookDto.setId(resultSet.getInt("ID"));
				bookDto.setTitle(resultSet.getString("TITLE"));
				bookDto.setAuthor(resultSet.getString("AUTHOR"));
				return bookDto;
			}
		});
	}

	public List<Map<String, Object>> queryMap() {

		return jdbcTemplate.queryForList("select ID,TITLE,AUTHOR from LIBTEST");
	}

	public void insertIntoDatabase() {

		int rowsInserted = jdbcTemplate.update(
				"INSERT INTO LIBTEST (TITLE, AUTHOR) VALUES" + " ('Ramses', 'Jacq'),('Alexandros', 'Manfredi')");

		System.out.println("Number of rows inserted: " + rowsInserted);
	}

	public void updateDataBase(String title, int id) {
		String update = "update LIBTEST set title = ? where ID = ?";

		int afterUpdate = jdbcTemplate.update(update, title, id);
		if (afterUpdate != 0)
			System.out.println("Employee data updated for ID " + id);
		else
			System.out.println("No Employee found with ID " + id);
	}

	public void deleteRow(int id) {
		String delete = "delete from LIBTEST where ID = ?";

		int afterDelete = jdbcTemplate.update(delete, id);

		if (afterDelete != 0)
			System.out.println("Employee data deleted for ID " + id);
		else
			System.out.println("No Employee found with ID " + id);
	}

}
