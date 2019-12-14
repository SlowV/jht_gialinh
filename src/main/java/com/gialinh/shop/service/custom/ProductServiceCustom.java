package com.gialinh.shop.service.custom;

import com.gialinh.shop.domain.Product;
import com.gialinh.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceCustom {
    @Autowired
    ProductRepository productRepository;
    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAllNoPage(){
        return productRepository.findAll();
    }
}
