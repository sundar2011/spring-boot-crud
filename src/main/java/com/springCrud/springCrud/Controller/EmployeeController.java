package com.springCrud.springCrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springCrud.springCrud.Exception.ResourceNotFound;
import com.springCrud.springCrud.model.Employee;
import com.springCrud.springCrud.repository.EmployeeRepository;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRespository;
	
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeRespository.findAll();
	}
		
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFound {
		Employee employee = employeeRespository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFound("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		return employeeRespository.save(employee);
	}	
			
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable (value="id") Long empId,@RequestBody Employee employeeDetails)throws ResourceNotFound{
		
		
		Employee updateEmployee=employeeRespository.findById(empId).
				orElseThrow(()->new ResourceNotFound("Employee id not found"+empId));
		
//		employeeRespository.delete(updateEmployee);
		updateEmployee.setEmail(employeeDetails.getEmail());
		updateEmployee.setFirstName(employeeDetails.getFirstName());
		updateEmployee.setLastName(employeeDetails.getLastName());
		final Employee updateEmployeeDetails =employeeRespository.save(updateEmployee);		
		return ResponseEntity.ok(updateEmployeeDetails);	
	}


	@DeleteMapping("/employees/{id}")
	public Map<String,Boolean> DeleteEmployee(@PathVariable (value="id") Long empId)throws ResourceNotFound{
		
		
		Employee updateEmployee=employeeRespository.findById(empId).
				orElseThrow(()->new ResourceNotFound("Employee id not found"+empId));
		
    	employeeRespository.delete(updateEmployee);
		Map<String,Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
