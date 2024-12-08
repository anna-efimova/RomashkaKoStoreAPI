package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.repository.ProductRepository;
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
        return new ProductDTO(findProductOrThrow(id));
    }

    public ProductEntity findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(ProductDTO::new)
            .toList();
    }

    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
        ProductEntity product = new ProductEntity();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setInStock(productCreateDTO.isInStock());
        ProductEntity savedProduct = productRepository.save(product);
        return new ProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductCreateDTO productCreateDTO) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setInStock(productCreateDTO.isInStock());
        productRepository.save(product);
        return new ProductDTO(product);

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

