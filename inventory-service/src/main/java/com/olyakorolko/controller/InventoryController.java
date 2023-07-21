package com.olyakorolko.controller;

import com.olyakorolko.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.allAreInStock(skuCodes);
    }
}
