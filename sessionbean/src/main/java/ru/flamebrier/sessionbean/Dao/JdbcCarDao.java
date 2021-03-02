package ru.flamebrier.sessionbean.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import ru.flamebrier.sessionbean.Exceptions.DaoException;
import ru.flamebrier.sessionbean.Models.Car;

@Singleton
@LocalBean
public class JdbcCarDao implements CarDao {

	private Connection conn;
	
	@PostConstruct
	public void initialize() throws DaoException {
		try {
			this.conn = getConn();
		} catch (SQLException e) {
			 throw new DaoException("Can`t get connection", e);
		} 
	}
	
	@Override
	public boolean create(Car t) throws DaoException {
		
		PreparedStatement prepState = null;
		
		try {
			String sql = "INSERT INTO car (model, brand, year) VALUES (?, ?, ?)";
			prepState = conn.prepareStatement(sql);
			prepState.setString(1, t.getModel());
			prepState.setNString(2, t.getBrand());
			prepState.setLong(3, t.getYear());
			int countOfUpdLines = prepState.executeUpdate();
			
			if (countOfUpdLines > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			throw new DaoException("Can`t create Car", e);
		} finally {
			
			try {
				prepState.close();
				prepState = null;
			} catch (SQLException e) {
				throw new DaoException("Can`t close prepared statement", e);
			}
		}
		
		return false;
	}

	@Override
	public List<Car> getAll() throws DaoException {
		
		List<Car> cars = new ArrayList<Car>();
	    Statement state = null;
		ResultSet resultSet = null;
		 
		try {
			String sql = "SELECT * FROM car";
			state = conn.createStatement();
			resultSet = state.executeQuery(sql);
			
			while(resultSet.next()) {
				long id = resultSet.getLong(1);
				String model = resultSet.getString(2);
				String brand = resultSet.getString(3);
				int year = resultSet.getInt(4);
				cars.add(new Car(id, model, brand, year));
			}
		} catch (SQLException e) {
			 throw new DaoException("Can`t get all cars" ,e);
		} finally {
			try {
				resultSet.close();
				resultSet = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close result set", e);
			}
			try {
				state.close();
				state = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close statement", e);
			}
		}
				
		return cars;
	}

	@Override
	public Car getById(long id) throws DaoException {
		
		Car car = null;
	    PreparedStatement prepState = null;
		ResultSet resultSet = null;
		
		try {
			String sql = "SELECT * FROM car WHERE id = ?";
			prepState = conn.prepareStatement(sql);
			prepState.setLong(1, id);
			resultSet = prepState.executeQuery();
			if (resultSet.next()) {
				String model = resultSet.getString(2);
				String brand = resultSet.getString(3);
				int year = resultSet.getInt(4);
				car = new Car(id, model, brand, year);
			}
		} catch (SQLException e) {
			 throw new DaoException("Can`t get car by id " + id , e);
		} finally {
			try {
				resultSet.close();
				resultSet = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close result set", e);
			}
			try {
				prepState.close();
				prepState = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close prepared statement", e);
			}
		}
		
		return car;
	}

	@Override
	public boolean update(Car t) throws DaoException {
		
		PreparedStatement prepState = null;
		
		try {
			String sql = "UPDATE car SET model = ?, brand = ?, year = ? WHERE id = ?";
			prepState = conn.prepareStatement(sql);
			prepState.setString(1, t.getModel());
			prepState.setString(2, t.getBrand());
			prepState.setLong(3, t.getYear());
			prepState.setLong(4, t.getId());
			int countOfUpdLines = prepState.executeUpdate();

			if (countOfUpdLines > 0) {
				return true;
			}
		} catch (SQLException e) {
			 throw new DaoException("Can`t update car by id " + t.getId(), e);
		} finally {
			try {
				prepState.close();
				prepState = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close prepared statement", e);
			}
		}
		
		return false;
	}

	@Override
	public boolean delete(long id) throws DaoException {
		
		PreparedStatement prepState = null;
		
		try {
			String sql = "DELETE FROM car WHERE id = ?";
			prepState = conn.prepareStatement(sql);
			prepState.setLong(1, id);
			int countOfUpdLines = prepState.executeUpdate();
			
			if (countOfUpdLines > 0) {
				return true;
			}
		} catch (SQLException e) {
			 throw new DaoException("Can`t delete car by id " + id, e);
		} finally {
			try {
				prepState.close();
				prepState = null;
			} catch (SQLException e) {
				 throw new DaoException("Can`t close prepared statement", e);
			}
		}
		
		return false;
	}
	
	@PreDestroy
	public void cleanup() throws DaoException {
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			 throw new DaoException("Can`t close connection", e);
		}
	}
	
	public Connection getConn() throws SQLException {
		
		String url = "jdbc:mysql://mysql/carshop?useSSL=false&user=root&password=root&serverTimezone=Europe/Moscow";
		return DriverManager.getConnection(url);
	}
	
}
