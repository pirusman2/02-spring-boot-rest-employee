package com.peercode.springboot.demo.dao;

import com.peercode.springboot.demo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById (int  theId);



}
