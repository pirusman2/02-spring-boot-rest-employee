package com.peercode.springboot.demo.dao;

import com.peercode.springboot.demo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

    // define field for entity manager
    private EntityManager entityManager;

    // define constructor injection

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager TheEntityManager){
       entityManager = TheEntityManager; // entitymanager is variable
        // TheEntity one is given by spring
    // entity manager is used for database connectivity it is a tool like for a worker



    }


    @Override
    public List<Employee> findAll() {

        // create query
        TypedQuery<Employee> theQuery = entityManager.createQuery //Hey EntityManager, create a query for me.
                                                       //query that produces employees
                ("from Employee",Employee.class);

        // execute query and get results
        List<Employee> employees = theQuery.getResultList();



        // return the results

        return employees;
    }

    @Override
    public Employee findById(int theId) {

        // get employee

        Employee theEmployee = entityManager.find(Employee.class,theId);

        // return employee

        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {

        // save employee

        Employee dbEmployee = entityManager.merge(theEmployee);

        // return the dbemployee

        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {

        // find employee by id

        Employee theEmployee = entityManager.find(Employee.class,theId);

        // remove employee

        entityManager.remove(theEmployee);

    }
}
