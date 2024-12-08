package com.example.romashkastoreapi.dto.sale;

import jakarta.validation.constraints.*;

public class SaleCreateDTO {
    @NotBlank
    @Size(max = 255)
    private String documentName;

    @Positive
    private long productId;

    @Min(1)
    private int quantity;

    public @NotBlank @Size(max = 255) String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(@NotBlank @Size(max = 255) String documentName) {
        this.documentName = documentName;
    }

    @Positive
    public long getProductId() {
        return productId;
    }

    public void setProductId(@Positive long productId) {
        this.productId = productId;
    }

    @Min(1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(1) int quantity) {
        this.quantity = quantity;
    }
}
