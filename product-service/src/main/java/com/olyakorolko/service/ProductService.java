package com.olyakorolko.service;

import com.olyakorolko.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.olyakorolko.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
