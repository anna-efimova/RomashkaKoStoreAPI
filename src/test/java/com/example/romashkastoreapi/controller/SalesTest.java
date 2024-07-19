package com.example.romashkastoreapi.controller;

import com.example.romashkastoreapi.dto.product.ProductCreateDTO;
import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.dto.sale.SaleCreateDTO;
import com.example.romashkastoreapi.service.ProductService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SalesTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void salesCreateTest() throws Exception {
        ProductDTO product = productService.createProduct(createProductDTO(100, BigDecimal.valueOf(2.0)));

        SaleCreateDTO sale1 = new SaleCreateDTO();
        sale1.setProductId(product.getId());
        sale1.setDocumentName("1");
        sale1.setQuantity(10);
        mockMvc.perform(post("/api/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.purchasePrice").value(BigDecimal.valueOf(20.0).toString()))
            .andExpect(jsonPath("$.product.quantity").value(90));

        SaleCreateDTO sale2 = new SaleCreateDTO();
        sale2.setProductId(product.getId());
        sale2.setDocumentName("2");
        sale2.setQuantity(90);
        mockMvc.perform(post("/api/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale2)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.purchasePrice").value(BigDecimal.valueOf(180.0).toString()))
            .andExpect(jsonPath("$.product.quantity").value(0))
            .andExpect(jsonPath("$.product.inStock").value(false));
    }

    @Test
    public void salesDeleteTest() throws Exception {
        ProductDTO product = productService.createProduct(createProductDTO(20, BigDecimal.valueOf(2.0)));

        SaleCreateDTO sale1 = new SaleCreateDTO();
        sale1.setProductId(product.getId());
        sale1.setDocumentName("3");
        sale1.setQuantity(15);
        MvcResult result = mockMvc.perform(post("/api/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.product.quantity").value(5))
            .andReturn();

        long saleId = Long.parseLong(JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString());

        mockMvc.perform(delete("/api/sales/" + saleId));

        ProductDTO updatedProduct = productService.getProductById(product.getId());

        Assertions.assertEquals(20, updatedProduct.getQuantity());
    }

    @Test
    public void salesUpdateTest() throws Exception {
        ProductDTO product1 = productService.createProduct(createProductDTO(100, BigDecimal.valueOf(2.0)));
        ProductDTO product2 = productService.createProduct(createProductDTO(20, BigDecimal.valueOf(3.0)));

        SaleCreateDTO sale1 = new SaleCreateDTO();
        sale1.setProductId(product1.getId());
        sale1.setDocumentName("11");
        sale1.setQuantity(10);
        MvcResult result = mockMvc.perform(post("/api/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.quantity").value(sale1.getQuantity()))
            .andExpect(jsonPath("$.purchasePrice").value(BigDecimal.valueOf(20.0).toString()))
            .andExpect(jsonPath("$.product.quantity").value(90))
            .andReturn();
        long saleId = Long.parseLong(JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString());

        SaleCreateDTO saleUpdate = new SaleCreateDTO();
        saleUpdate.setProductId(product2.getId());
        saleUpdate.setDocumentName("22");
        saleUpdate.setQuantity(15);
        mockMvc.perform(put("/api/sales/" + saleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saleUpdate)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.documentName").value(saleUpdate.getDocumentName()))
            .andExpect(jsonPath("$.quantity").value(saleUpdate.getQuantity()))
            .andExpect(jsonPath("$.purchasePrice").value(BigDecimal.valueOf(45.0).toString()))
            .andExpect(jsonPath("$.product.quantity").value(5))
            .andExpect(jsonPath("$.product.id").value(product2.getId()));

        ProductDTO updatedProduct1 = productService.getProductById(product1.getId());
        Assertions.assertEquals(100, updatedProduct1.getQuantity());

        ProductDTO updatedProduct2 = productService.getProductById(product2.getId());
        Assertions.assertEquals(5, updatedProduct2.getQuantity());
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
