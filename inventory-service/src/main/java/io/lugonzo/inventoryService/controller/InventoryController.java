package io.lugonzo.inventoryService.controller;

import io.lugonzo.inventoryService.model.Inventory;
import io.lugonzo.inventoryService.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    @GetMapping("/{skuCode}")
    public Boolean IsInStock(@PathVariable String skuCode){

        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(()-> new RuntimeException("cannot find product by sku code " + skuCode));

        return inventory.getStock() > 0;
    }
}
