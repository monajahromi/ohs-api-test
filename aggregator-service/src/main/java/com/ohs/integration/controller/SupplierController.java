package com.ohs.integration.controller;

import com.ohs.integration.service.ProductService;
import com.ohs.integration.service.SupplierService;
import com.ohs.product.ProductResponse;
import com.ohs.supplier.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping(value = "{supplierPid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SupplierResponse getSupplier(@PathVariable String supplierPid){
        return this.supplierService.getSupplierByPid(supplierPid);
    }

}
