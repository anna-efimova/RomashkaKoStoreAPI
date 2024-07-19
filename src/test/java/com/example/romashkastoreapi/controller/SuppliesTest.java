package com.example.romashkastoreapi.controller;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.dto.sale.SaleCreateDTO;
import com.example.romashkastoreapi.dto.supply.SupplyCreateDTO;
import com.example.romashkastoreapi.service.ProductService;
import com.example.romashkastoreapi.service.SaleService;
import com.example.romashkastoreapi.service.SupplyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SuppliesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void supplyCreateTest() throws Exception {
        ProductDTO product = productService.createProduct(createProductDTO(0, BigDecimal.valueOf(2.0)));

        SupplyCreateDTO supply1 = new SupplyCreateDTO();
        supply1.setProductId(product.getId());
        supply1.setDocumentName("1");
        supply1.setQuantity(10);
        mockMvc.perform(post("/api/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supply1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.product.quantity").value(10))
            .andExpect(jsonPath("$.product.inStock").value(true));


        SupplyCreateDTO supply2 = new SupplyCreateDTO();
        supply2.setProductId(product.getId());
        supply2.setDocumentName("2");
        supply2.setQuantity(40);
        mockMvc.perform(post("/api/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supply2)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.product.quantity").value(50))
            .andExpect(jsonPath("$.product.inStock").value(true));
    }

    @Test
    public void supplyDeleteTest() throws Exception {
        ProductDTO product = productService.createProduct(createProductDTO(10, BigDecimal.valueOf(2.0)));

        SupplyCreateDTO supply1 = new SupplyCreateDTO();
        supply1.setProductId(product.getId());
        supply1.setDocumentName("1");
        supply1.setQuantity(33);
        MvcResult result = mockMvc.perform(post("/api/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supply1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.product.quantity").value(43))
            .andReturn();

        long supplyId = Long.parseLong(JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString());

        mockMvc.perform(delete("/api/supplies/" + supplyId));

        ProductDTO updatedProduct = productService.getProductById(product.getId());

        Assertions.assertEquals(10, updatedProduct.getQuantity());
    }

    @Test
    public void supplyUpdateTest() throws Exception {
        ProductDTO product1 = productService.createProduct(createProductDTO(0, BigDecimal.valueOf(2.0)));
        ProductDTO product2 = productService.createProduct(createProductDTO(20, BigDecimal.valueOf(3.0)));

        SupplyCreateDTO supply = new SupplyCreateDTO();
        supply.setProductId(product1.getId());
        supply.setDocumentName("11");
        supply.setQuantity(10);
        MvcResult result = mockMvc.perform(post("/api/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supply)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.product.quantity").value(10))
            .andExpect(jsonPath("$.product.inStock").value(true))
            .andReturn();

        long supplyId = Long.parseLong(JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString());

        SupplyCreateDTO supplyUpdate = new SupplyCreateDTO();
        supplyUpdate.setProductId(product2.getId());
        supplyUpdate.setDocumentName("12");
        supplyUpdate.setQuantity(14);
        mockMvc.perform(put("/api/supplies/" + supplyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplyUpdate)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.documentName").value(supplyUpdate.getDocumentName()))
            .andExpect(jsonPath("$.product.quantity").value(34))
            .andExpect(jsonPath("$.product.id").value(product2.getId()));

        ProductDTO updatedProduct1 = productService.getProductById(product1.getId());
        Assertions.assertEquals(0, updatedProduct1.getQuantity());
        Assertions.assertFalse(updatedProduct1.isInStock());

        ProductDTO updatedProduct2 = productService.getProductById(product2.getId());
        Assertions.assertEquals(34, updatedProduct2.getQuantity());
    }

    private ProductCreateDTO createProductDTO(int quantity, BigDecimal price) {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("Test");
        productCreateDTO.setQuantity(quantity);
        productCreateDTO.setPrice(price);
        productCreateDTO.setDescription("Test Description");
        return productCreateDTO;
    }
}
