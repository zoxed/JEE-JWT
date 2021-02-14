package com.sid.inventory.web;

import com.sid.inventory.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductRestController {
    @Autowired
 private ProductRepository productRepository;
}
