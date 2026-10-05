package com.parodiher.ecommerce.service;

import com.parodiher.ecommerce.exception.ProductNotFoundException;
import com.parodiher.ecommerce.model.Product;
import com.parodiher.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()){
            return null;
        }
        Product productToUpdate = existingProduct.get();
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());

        return productRepository.save(productToUpdate);
    }

    public void deleteProduct(Long id){
        Optional <Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()){
            productRepository.deleteById(id);
        }else{
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }
}

