package com.springCrud.springCrud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springCrud.springCrud.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
