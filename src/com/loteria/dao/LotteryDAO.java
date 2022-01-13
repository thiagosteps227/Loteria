package com.loteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.loteria.model.Draw;

public class LotteryDAO {

	private static final String SELECT_ALL_DRAWS = "SELECT * FROM draws;";
	private static final String INSERT_DRAWS_SQL = "INSERT INTO draws (numbers) VALUES (?);";
	private static final String UPDATE_DRAW = "update draws set numbers = ? where id = ?;";
	private static final String SELECT_DRAW_BY_ID = "select id,numbers from draws where id =?";
	private static final String DELETE_DRAW_BY_ID = "delete from draws where id = ?;";

	public List<Draw> selectAllDraws() {
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Draw> draws = new ArrayList<>();

		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DRAWS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String numbers = rs.getString("numbers");

				draws.add(new Draw(id, numbers));
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return draws;
	}

	public void insertDraws(Draw draw) throws SQLException {

		// try-with-resource statement will auto close the connection.
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DRAWS_SQL)) {
			preparedStatement.setString(1, draw.getDrawNumbers());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
	}

	public Draw selectDraw(int id) {
		Draw draw = null;
		// Step 1: Establishing a Connection
		try (Connection connection = JDBCUtils.getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DRAW_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int drawId = rs.getInt("id");
				String numbers = rs.getString("numbers");

				draw = new Draw(drawId, numbers);
			}
		} catch (SQLException exception) {
			JDBCUtils.printSQLException(exception);
		}
		return draw;
	}

	public boolean updateDraw(Draw draw) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_DRAW);) {

			System.out.println(draw.getDrawNumbers());

			statement.setString(1, draw.getDrawNumbers());
			statement.setInt(2, draw.getId());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public boolean deleteDraw(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_DRAW_BY_ID);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
}
