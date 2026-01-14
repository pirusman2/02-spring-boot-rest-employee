package com.peercode.springboot.demo.rest;


import com.peercode.springboot.demo.entity.Employee;
import com.peercode.springboot.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;

    private JsonMapper jsonMapper;

    // quick and dirty : inject employee dao

    @Autowired
    public EmployeeRestController (EmployeeService theEmployeeService, JsonMapper theJsonMapper){
        employeeService = theEmployeeService;
        jsonMapper = theJsonMapper;
    }


    // expose "/employee"  and return a list of employees
    @GetMapping("/employee")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    // add mapping for GET /employees/ {employeeId}

    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee theEmployee = employeeService.findById(employeeId);

        if (theEmployee == null){
            throw new RuntimeException("employee id not found : " +employeeId);
        }
        return theEmployee;
    }


    // add mapping for Post /employees -- add new employee

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        // also incase they just pass on id in
        // Json  ... set id to 0
        // this is to force save of new item instead of update

        theEmployee.setId(0);

        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;


    }

    // add mapping for Put /employee to update existing employee

    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;

    }

    // add mapping for patch

    @PatchMapping("/employee/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId,
                                  @RequestBody Map<String, Object> patchPayload){

        Employee tempEmployee = employeeService.findById(employeeId);

        // throw an exception if employee not found

        if(tempEmployee == null){
            throw new RuntimeException("Employee Id not found" +employeeId);
        }

        // throw exception if id is already available or exists
        if (patchPayload.containsKey("id")){
            throw new RuntimeException("Employee id not allowed bcx already exists"+employeeId);
        }

        Employee patchEmployee = jsonMapper.updateValue(tempEmployee,patchPayload);

        Employee dbEmployee = employeeService.save(patchEmployee);
        return dbEmployee;

    }



    // add mapping for deleting employe by id "/employee/{employeeId}"
    @DeleteMapping("employee/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee tempEmployee = employeeService.findById(employeeId);
        if (tempEmployee == null){
            throw new RuntimeException("Employee dont exist in database" +employeeId);
        }
        employeeService.deleteById(employeeId);
        return "Employee id deleted " +employeeId;
    }



}
