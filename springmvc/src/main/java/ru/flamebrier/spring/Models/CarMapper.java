package ru.flamebrier.spring.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CarMapper implements RowMapper<Car> {

	@Override
	public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		int id = rs.getInt("id");
		String model = rs.getString("model");
		short year = rs.getShort("year");
		
		return new Car(id, model, year);

	}
}