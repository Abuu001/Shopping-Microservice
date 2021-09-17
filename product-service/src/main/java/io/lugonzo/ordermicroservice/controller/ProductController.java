package io.lugonzo.ordermicroservice.controller;

import io.lugonzo.ordermicroservice.model.Product;
import io.lugonzo.ordermicroservice.repository.ProductRepository;
import io.lugonzo.ordermicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private  final ProductService productService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> allProducts(){
        return  productService.getAllProducts();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product){
         productService.postProduct(product);
    }
}
