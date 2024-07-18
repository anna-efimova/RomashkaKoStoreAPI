package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

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
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        ProductDTO foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(productEntity.getName(), foundProduct.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void shouldCreateProduct() {
        Mockito.when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDTO createdProduct = productService.createProduct(productCreateDTO);

        assertNotNull(createdProduct);
        assertEquals(productEntity.getName(), createdProduct.getName());
    }

    @Test
    void shouldUpdateProduct() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        Mockito.when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDTO updatedProduct = productService.updateProduct(1L, productCreateDTO);

        assertNotNull(updatedProduct);
        assertEquals(productEntity.getName(), updatedProduct.getName());
    }

    @Test
    void shouldDeleteProduct() {
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        Mockito.doNothing().when(productRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
    }

    @Test
    void shouldGetAllProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(productEntity.getName(), products.get(0).getName());
    }
}
