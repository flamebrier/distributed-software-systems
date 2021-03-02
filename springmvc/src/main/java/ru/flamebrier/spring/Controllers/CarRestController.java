package ru.flamebrier.spring.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.flamebrier.spring.DAO.JdbcCarDao;
import ru.flamebrier.spring.Models.Car;

@RestController
@RequestMapping("/api/car/")
public class CarRestController {
	
	private JdbcCarDao dao;

	@Autowired
	public void setDao(JdbcCarDao dao) {
		this.dao = dao;
	}

	@GetMapping("/all")
	public List<Car> getAll() {
		
		return dao.getAll();
	}
	
	@GetMapping("{id}")
	public Car getById(@PathVariable("id") int id) {
		
		Car car = dao.getById(id);
		
		return car;
	}
	
	@PostMapping("/create")
	public void createEmployee(@ModelAttribute Car car) {
		
		dao.insert(car);
	}
	
	@PostMapping("/car/update")
	public void updateEmployee(@ModelAttribute Car car) {
		
		dao.update(car);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public void deleteEmployee(@PathVariable("id") int id) {
		
		dao.delete(id);
	}
}