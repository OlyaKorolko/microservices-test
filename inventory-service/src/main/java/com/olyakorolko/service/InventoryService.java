package com.olyakorolko.service;

import com.olyakorolko.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean allAreInStock(List<String> skuCodes) {
        return skuCodes.stream()
                .allMatch(s -> inventoryRepository.findBySkuCode(s)
                        .map(inventory -> inventory.getQuantity() > 0)
                        .orElse(false));
    }
}
