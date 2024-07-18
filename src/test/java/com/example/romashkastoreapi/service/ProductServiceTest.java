package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private ProductEntity productEntity;
    private ProductCreateDTO productCreateDTO;

    @BeforeEach
    void setUp() {
        productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("Test Product");
        productEntity.setDescription("Test Description");
        productEntity.setPrice(BigDecimal.valueOf(10.0));
        productEntity.setInStock(true);

        productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Test Product");
        productCreateDTO.setDescription("Test Description");
        productCreateDTO.setPrice(BigDecimal.valueOf(10.0));
        productCreateDTO.setInStock(true);
    }

    @Test
    void shouldGetProductById() {
        ProductDTO newProduct = productService.createProduct(productCreateDTO);
        ProductDTO foundProduct = productService.getProductById(newProduct.getId());
        assertNotNull(foundProduct);
        assertEquals(productEntity.getName(), foundProduct.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999999L));
    }

    @Test
    void shouldCreateProduct() {
        ProductDTO createdProduct = productService.createProduct(productCreateDTO);

        assertNotNull(createdProduct);
        assertEquals(productEntity.getName(), createdProduct.getName());
    }

    @Test
    void shouldUpdateProduct() {
        ProductDTO newProduct = productService.createProduct(productCreateDTO);
        ProductDTO updatedProduct = productService.updateProduct(newProduct.getId(), productCreateDTO);

        assertNotNull(updatedProduct);
        assertEquals(productEntity.getName(), updatedProduct.getName());
    }

    @Test
    void shouldDeleteProduct() {
        ProductDTO newProduct = productService.createProduct(productCreateDTO);
        assertDoesNotThrow(() -> productService.deleteProduct(newProduct.getId()));
    }

    @Test
    void shouldGetAllProducts() {
        ProductDTO newProduct = productService.createProduct(productCreateDTO);
        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().map(ProductDTO::getId).collect(Collectors.toSet()).contains(newProduct.getId()));
    }
}
