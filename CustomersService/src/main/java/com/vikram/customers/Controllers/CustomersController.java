package com.vikram.customers.Controllers;

import com.vikram.customers.Models.Customer;
import com.vikram.customers.Models.Rewards;
import com.vikram.customers.Repos.CustomerRepo;
import com.vikram.customers.Services.CustomerService;
import com.vikram.customers.Utils.Utility;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/customers")
public class CustomersController {

    @Value("${rewards.service.url}")
    String addressServiceUrl;

    @Autowired
    CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping
    String insertData(@RequestBody Customer customer) {
        if (customer == null)
            return "Data not inserted successfully";

        customerService.insertData(customer);
        return "Data inserted successfully";
    }

    @PutMapping
    String updateData(@RequestBody Customer customer) {
        if (customer == null) {
            return "Data not inserted successfully";
        }
        //  collageService.insertData(collage);
        if (customer.getId() != null && customerRepo.findById(customer.getId()).isPresent()) {
            customerService.insertData(customer);
            return "Data Updated successfully";
        } else {
            return "Data not inserted successfully, Create before updating";
        }
    }

    @PatchMapping
    String updateDataPatch(@RequestBody Customer customer) {
        Optional<Customer> customerOptional = customerRepo.findById(customer.getId());

        if (!customerOptional.isPresent()) {
            return "Record not found";
        }
        try {
            Utility.customerPatcher(customerOptional.get(), customer);
            customerService.insertData(customerOptional.get());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "Data patched successfully";
    }

    @GetMapping
    List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    String getRequest() {
        return "Success";
    }

    @GetMapping("/{id}")
    Customer getCustomerById(@PathVariable int id) {
        Customer customer = customerRepo.findById(id).get();
        return customer;
    }

    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/rewards/{id}")
    Customer getRewardsOfCustomerById(@PathVariable int id) {
        Customer customer = customerRepo.findById(id).get();
        ResponseEntity<List<Rewards>> response = null;

        try {
            response = restTemplate.exchange("http://" + addressServiceUrl + customer.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Rewards>>() {
            });
        }
        catch (Exception e) {}
        customer.setTotalRewardsPoints(response!=null?response.getBody().stream().mapToInt(e->e.getRewardPoints()).sum():0);
        return customer;
    }

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> getOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.OPTIONS, HttpMethod.HEAD)
                .build();
    }


    @DeleteMapping("/{id}")
    String deleteCustomerById(@PathVariable int id) {
        if (customerRepo.findById(id).isPresent()) {
            customerRepo.deleteById(id);
            return "Customer deleted successfully";
        }
        return "Failed to Delete the customer, Pls check the id";
    }
}