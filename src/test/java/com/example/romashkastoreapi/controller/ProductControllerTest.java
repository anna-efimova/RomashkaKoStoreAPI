package com.example.romashkastoreapi.controller;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ProductService productService;

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
        productCreateDTO.setQuantity(10);
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldCreateProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCreateDTO)))
//            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldGetProductById() throws Exception {
        long id = productService.createProduct(productCreateDTO).getId();
        mockMvc.perform(get("/api/products/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        long id = productService.createProduct(productCreateDTO).getId();
        mockMvc.perform(put("/api/products/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCreateDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        long id = productService.createProduct(productCreateDTO).getId();
        mockMvc.perform(delete("/api/products/" + id))
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

