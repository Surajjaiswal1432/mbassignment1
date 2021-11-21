package com.mbassignment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mbassignment.dao.EmployeeDao;
import com.mbassignment.dao.ManagerDao;
import com.mbassignment.model.Employee;
import com.mbassignment.model.Manager;
import com.mbassignment.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Value("${item.per.page}")
	private int itemPerPage;
	@Autowired
	private EmployeeDao dao;

	@Autowired
	private ManagerDao managerDao;

	@Override
	public Employee saveRecored(Employee employee) {

		Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

		String currentuser = authentication1.getName();

		Manager manager = managerDao.findByEmail(currentuser);
		employee.setManager(manager);
		return dao.saveRecored(employee);
	}

	@Override
	public Page<Employee> getAllEmployessByPagination(int id) {
		PageRequest pages = PageRequest.of(id - 1, itemPerPage, Sort.by("firstname"));

		return dao.getAllEmployessByPagination(pages);
	}

	@Override
	public int deleteEmploye(int id) {

		return dao.deleteEmployeeById(id);
	}

	@Override
	public Employee findEmployeeById(int id) {

		return dao.findEmployeeById(id);
	}

	@Override
	public void updateEmployeeForm(Employee employee) {
		dao.updateEmployeeRecored(employee);

	}

	@Override
	public void common(int startPage, EmployeeService service, Model model) {

		Page<Employee> page = service.getAllEmployessByPagination(startPage);
		List<Employee> employees = page.getContent();
		model.addAttribute("employees", employees);
		model.addAttribute("currentPage", startPage);
		model.addAttribute("totalPages", page.getTotalPages());

	}

}
