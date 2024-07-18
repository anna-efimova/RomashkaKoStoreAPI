package com.example.romashkastoreapi.dto.supply;

import com.example.romashkastoreapi.model.ProductEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

public class SupplyCreateDTO {
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
