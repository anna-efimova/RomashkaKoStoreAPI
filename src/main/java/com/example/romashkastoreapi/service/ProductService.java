package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(
            ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
        ProductDTO product = new ProductDTO();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setInStock(productCreateDTO.isInStock());
        return productRepository.save(product);
    }

    public ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        ProductDTO product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setInStock(productCreateDTO.isInStock());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        ProductDTO product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        productRepository.deleteById(id);
    }
}

