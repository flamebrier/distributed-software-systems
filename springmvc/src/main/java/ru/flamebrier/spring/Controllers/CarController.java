package ru.flamebrier.spring.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.flamebrier.spring.DAO.JdbcCarDao;
import ru.flamebrier.spring.Models.Car;

@Controller
@RequestMapping("/")
public class CarController {
	
	private JdbcCarDao dao;

	@Autowired
	public void setDao(JdbcCarDao dao) {
		this.dao = dao;
	}
	
	@GetMapping()
	public String getAllEmployees(Model model) {
		
		List<Car> carList = dao.getAll();
		model.addAttribute("carList", carList);
		return "car/index";
	}
	
	@GetMapping("car/create")
	public String getCreatePage(Model model) {
		
		model.addAttribute("car", new Car());
		return "car/create";
	}
	
	@PostMapping("car/create")
	public String createCar(@ModelAttribute Car car) {
		
		dao.insert(car);
		return "redirect:/";
	}
	
	@GetMapping("car/details/{id}")
	public String getCarDetails(@PathVariable("id") int id, Model model) {
		
		Car car = dao.getById(id);
		model.addAttribute("car", car);
		return "car/details";
	}
	
	@PostMapping("/car/update")
	public String updateEmployee(@ModelAttribute Car car) {
		
		dao.update(car);
		return "redirect:/car/details/" + car.getId();
	}
	
	@GetMapping("/car/delete")
	public String deleteEmployee(@RequestParam(value = "id") int id) {
		dao.delete(id);
		return "redirect:/";
	}
}