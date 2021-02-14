package com.sid.billingservice;

import com.sid.billingservice.entities.Bill;
import com.sid.billingservice.entities.ProductItem;
import com.sid.billingservice.feign.CustomerRestClient;
import com.sid.billingservice.feign.ProductItemRestClient;
import com.sid.billingservice.model.Customer;
import com.sid.billingservice.model.Product;
import com.sid.billingservice.repositories.BillRepository;
import com.sid.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;


@SpringBootApplication
@EnableFeignClients
public class BillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }
    @Bean
    CommandLineRunner start (BillRepository billRepository,
                             ProductItemRepository productItemRepository,
                             CustomerRestClient customerRestClient,
                             ProductItemRestClient productItemRestClient
    ){
        return args -> {
            Customer customer= customerRestClient.getCustomerById(1L);
            Bill facture = billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel = productItemRestClient.pageProducts();
            productPagedModel.forEach( produit -> {
                ProductItem productItem = new ProductItem();
                productItem.setPrice(produit.getPrice());
                productItem.setQuantity((double) (1+new Random().nextInt(100)));
                productItem.setBill(facture);
                productItem.setProductId(produit.getId());
                productItemRepository.save(productItem);
            });
        };
    }
}
