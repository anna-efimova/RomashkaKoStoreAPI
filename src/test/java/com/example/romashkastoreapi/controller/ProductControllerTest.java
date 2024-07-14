package com.example.romashkastoreapi.controller;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;
    private ProductCreateDTO productCreateDTO;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(BigDecimal.valueOf(10.0));
        productDTO.setInStock(true);

        productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Test Product");
        productCreateDTO.setDescription("Test Description");
        productCreateDTO.setPrice(BigDecimal.valueOf(10.0));
        productCreateDTO.setInStock(true);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(Collections.singletonList(productDTO));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(productDTO.getName()));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        Mockito.when(productService.createProduct(Mockito.any())).thenReturn(productDTO);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldGetProductById() throws Exception {
        Mockito.when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any())).thenReturn(productDTO);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn400ForInvalidProduct() throws Exception {
        ProductCreateDTO invalidProduct = new ProductCreateDTO();
        invalidProduct.setName("");
        invalidProduct.setDescription("This description exceeds the limit of 4096 characters. " + "a".repeat(4097));
        invalidProduct.setPrice(BigDecimal.valueOf(-1));

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }
}

