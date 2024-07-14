//package com.example.romashkastoreapi.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import jdk.jfr.Enabled;
//
//import java.math.BigDecimal;
//
//@Entity
//public class SaleEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @Size(max = 255)
//    private String documentName;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private ProductEntity product;
//
//    @Min(1)
//    private int quantity;
//
//    @DecimalMin("0.0")
//    private BigDecimal purchasePrice;
//
//
//}
