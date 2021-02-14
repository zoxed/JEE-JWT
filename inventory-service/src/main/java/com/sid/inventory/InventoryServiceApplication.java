package com.sid.inventory;

import com.sid.inventory.entities.Product;
import com.sid.inventory.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        restConfiguration.exposeIdsFor(Product.class);
      return args -> {
        productRepository.save(new Product(null,"Dell G3",6876687,20));
        productRepository.save(new Product(null,"PlayStation 4",6687,23));
        productRepository.save(new Product(null,"Controller",686,65));
        productRepository.findAll().forEach(System.out::println);

      };
    }
}
