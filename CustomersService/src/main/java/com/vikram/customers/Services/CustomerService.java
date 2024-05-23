package com.vikram.customers.Services;

import com.vikram.customers.Models.Customer;
import com.vikram.customers.Repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

//@Scope("prototype")
@Service
public class CustomerService {
    @Autowired
    CustomerRepo repo;

    public void insertData(Customer customer) {
        Customer c = repo.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer>  customers =  repo.findAll();

        return customers;
    }
}