package com.example.romashkastoreapi.controller;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.repository.ProductRepository;
import com.example.romashkastoreapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        var productCreateDTO1 = new ProductCreateDTO();
        productCreateDTO1.setName("111");
        productCreateDTO1.setDescription("Test Description");
        productCreateDTO1.setPrice(BigDecimal.valueOf(1));
        productCreateDTO1.setInStock(true);
        productService.createProduct(productCreateDTO1);

        var productCreateDTO2 = new ProductCreateDTO();
        productCreateDTO2.setName("222");
        productCreateDTO2.setDescription("Test Description");
        productCreateDTO2.setPrice(BigDecimal.valueOf(2));
        productCreateDTO2.setInStock(true);
        productService.createProduct(productCreateDTO2);

        var productCreateDTO3 = new ProductCreateDTO();
        productCreateDTO3.setName("123");
        productCreateDTO3.setDescription("Test Description");
        productCreateDTO3.setPrice(BigDecimal.valueOf(3));
        productCreateDTO3.setInStock(false);
        productService.createProduct(productCreateDTO3);
    }

    @Test
    void filterByName() throws Exception {
        mockMvc.perform(get("/api/products/filter")
                .param("name", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/api/products/filter")
                .param("name", "123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void filterByAndPrice() throws Exception {
        mockMvc.perform(get("/api/products/filter")
                .param("name", "1")
                .param("priceFrom", "1")
                .param("priceTo", "1")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/products/filter")
                .param("name", "1")
                .param("priceFrom", "2")
                .param("priceTo", "2")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void filterByNameAndPagination() throws Exception {
        mockMvc.perform(get("/api/products/filter")
                .param("name", "2")
                .param("page", "0")
                .param("size", "2")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/api/products/filter")
                .param("name", "2")
                .param("page", "0")
                .param("size", "1")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void filterByNameAndPaginationAndSort() throws Exception {
        mockMvc.perform(get("/api/products/filter")
                .param("name", "2")
                .param("page", "0")
                .param("size", "1")
                .param("sort", "name,ASC")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("123"));

        mockMvc.perform(get("/api/products/filter")
                .param("name", "2")
                .param("page", "0")
                .param("size", "1")
                .param("sort", "name,DESC")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("222"));
    }
}

