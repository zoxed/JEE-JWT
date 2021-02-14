package com.sid.customer.web;

import com.sid.customer.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRestController {
    @Autowired
    private CustomerRepository customerRepository;

}
