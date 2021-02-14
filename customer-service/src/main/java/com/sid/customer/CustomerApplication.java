package com.sid.customer;

import com.sid.customer.entities.Customer;
import com.sid.customer.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRep, RepositoryRestConfiguration restConfiguration) {
        restConfiguration.exposeIdsFor(Customer.class);
        return args -> {
            customerRep.save(new Customer(null, "mohamed", "simon.kaabi@gmail.com"));
            customerRep.save(new Customer(null, "rachid", "rachid.kaabi@gmail.com"));
            customerRep.save(new Customer(null, "hamid", "hamid.kaabi@gmail.com"));

        };

    }
}
