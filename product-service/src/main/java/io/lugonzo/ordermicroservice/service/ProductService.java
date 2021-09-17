package io.lugonzo.ordermicroservice.service;

import io.lugonzo.ordermicroservice.model.Product;
import io.lugonzo.ordermicroservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private  final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void postProduct(Product product){
         productRepository.save(product);
    }
}
