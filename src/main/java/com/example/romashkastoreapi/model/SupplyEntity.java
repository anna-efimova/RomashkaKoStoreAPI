package com.example.romashkastoreapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

@Table(name = "supply")
@Entity
public class SupplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Min(1)
    private int quantity;


    public @Size(max = 255) String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(@Size(max = 255) String documentName) {
        this.documentName = documentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Min(1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(1) int quantity) {
        this.quantity = quantity;
    }
}
