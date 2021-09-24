package io.lugonzo.orderservice.controller;

import io.lugonzo.orderservice.client.InventoryClient;
import io.lugonzo.orderservice.dto.OrderDto;
import io.lugonzo.orderservice.models.Order;
import io.lugonzo.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private  final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final Resilience4jCircuitBreakerFactory circuitBreakerFactory;

    @PostMapping
    public String placeOrder(@RequestBody OrderDto orderDto){

        Resilience4jCircuitBreakerFactory circuitBreaker = circuitBreakerFactory.create("inventory");
        Supplier<Boolean> booleanSupplier =()->orderDto.getOrderLineItemsList().stream()
                .allMatch(orderLineItems -> inventoryClient.checkStock(orderLineItems.getSkuCode()));

        boolean productsInStock = circuitBreaker.run(booleanSupplier,throwable -> handleErrorCase());

        if(productsInStock) {
            Order order = new Order();
            order.setOrderLineItems(orderDto.getOrderLineItemsList());
            order.setOrderNumber(UUID.randomUUID().toString());
            orderRepository.save(order);

            return "Order placed Successfully";
        }

        return "Order Failed Please Tyr again";
    }

    private Boolean handleErrorCase(){
        return false;
    }

}
