package ru.flamebrier.spring.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import ru.flamebrier.spring.Models.Car;
import ru.flamebrier.spring.Models.CarMapper;

@Repository
public class JdbcCarDao extends JdbcDaoSupport implements Dao<Car> {
	
	@Autowired
	public JdbcCarDao(JdbcTemplate jdbcTemplate) {
		super.setJdbcTemplate(jdbcTemplate);
	}

	@Override
	public List<Car> getAll() {
		
		String sql = "SELECT * FROM car";
		return super.getJdbcTemplate().query(sql, new CarMapper());
	}
	
	@Override
	public Car getById(int id) {
		
		String sql = "SELECT * FROM car WHERE id = "+id;
		return super.getJdbcTemplate().queryForObject(sql, new CarMapper());
	}

	@Override
	public boolean insert(Car t) {
		
		String sql = "INSERT INTO car(model, year) VALUES(?, ?)";
		return super.getJdbcTemplate().update(sql, t.getModel(), t.getYear()) > 0;
	}

	@Override
	public boolean update(Car t) {
		
		String sql = "UPDATE car SET model=?, year=? WHERE id=?";
		return super.getJdbcTemplate().update(sql, t.getModel(), t.getYear(), t.getId()) > 0;
	}

	@Override
	public boolean delete(int id) {
		
		String sql = "DELETE FROM car WHERE id=?";
		return super.getJdbcTemplate().update(sql, id) > 0;
	}
}