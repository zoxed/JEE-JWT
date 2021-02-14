package com.sid.inventory.entities;

import org.springframework.data.rest.core.config.Projection;
@Projection(types =Product.class ,name = "fullproduct")
public interface ProductProjection extends Projection {
    public Long getId();
    public String getName();
    public Double getPrice();
}
