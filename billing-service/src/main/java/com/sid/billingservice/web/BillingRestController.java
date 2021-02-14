package com.sid.billingservice.web;

import com.sid.billingservice.entities.Bill;
import com.sid.billingservice.feign.CustomerRestClient;
import com.sid.billingservice.feign.ProductItemRestClient;
import com.sid.billingservice.model.Customer;
import com.sid.billingservice.model.Product;
import com.sid.billingservice.repositories.BillRepository;
import com.sid.billingservice.repositories.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;
    private ProductItemRepository productItemRepository;

    public BillingRestController(BillRepository billRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient, ProductItemRepository productItemRepository) {
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
        this.productItemRepository = productItemRepository;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill facture= billRepository.findById(id).get();
        Customer client = customerRestClient.getCustomerById(facture.getCustomerID());
        facture.setCustomer(client);
        facture.getProductItems().forEach(proItem -> {
            Product product = productItemRestClient.getProductById(proItem.getProductId());
            //proItem.setProduct(product);
            proItem.setProductName(product.getName());

        });
        return facture;
    }

}
