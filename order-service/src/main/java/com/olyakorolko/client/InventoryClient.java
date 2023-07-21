package com.olyakorolko.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory")
    Boolean isInStock(@RequestParam List<String> skuCodes);
}
