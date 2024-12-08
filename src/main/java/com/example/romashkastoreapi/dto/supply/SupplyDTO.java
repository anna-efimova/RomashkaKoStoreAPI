package com.example.romashkastoreapi.dto.supply;

import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.model.SupplyEntity;

public class SupplyDTO {

    private Long id;
    private String documentName;
    private ProductDTO product;
    private int quantity;

    public SupplyDTO(SupplyEntity supplyEntity) {
        this.id = supplyEntity.getId();
        this.documentName = supplyEntity.getDocumentName();
        this.product = new ProductDTO(supplyEntity.getProduct());
        this.quantity = supplyEntity.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
