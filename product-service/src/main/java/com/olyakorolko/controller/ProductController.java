package com.olyakorolko.controller;

import com.olyakorolko.dto.ProductInDTO;
import com.olyakorolko.dto.ProductMapper;
import com.olyakorolko.dto.ProductOutDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.olyakorolko.service.ProductService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOutDTO addProduct(@RequestBody ProductInDTO productInDTO) {
        ProductOutDTO product = productMapper.toDto(productService.addProduct(productMapper.fromDto(productInDTO)));
        log.info("Product " + product.getId() + " is saved");
        return product;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductOutDTO> getAllProducts() {
        return productService.findAll().stream().map(productMapper::toDto).collect(toList());
    }
}
