package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.dto.sale.SaleCreateDTO;
import com.example.romashkastoreapi.dto.sale.SaleDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SaleEntity;
import com.example.romashkastoreapi.repository.ProductRepository;
import com.example.romashkastoreapi.repository.SaleRepository;
import com.example.romashkastoreapi.repository.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SupplyRepository supplyRepository;



    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, SupplyRepository supplyRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.supplyRepository = supplyRepository;
    }

    public SaleDTO getSaleById(Long id) {
        return new SaleDTO(findProductOrThrow(id));
    }

    public SaleEntity findProductOrThrow(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id " + id));
    }

    public List<SaleDTO> getAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(SaleDTO::new)
                .toList();
    }

    public SaleDTO createSale(SaleCreateDTO saleCreateDTO) {
        ProductEntity product = productRepository.findById(saleCreateDTO.getProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (saleCreateDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Sale quantity must be greater than zero");
        }
        int totalSuppliedQuantity = supplyRepository.findTotalSuppliedQuantityByProductId(product.getId());
        int totalSoldQuantity = saleRepository.findTotalSoldQuantityByProductId(product.getId());
        int availableQuantity = totalSuppliedQuantity - totalSoldQuantity;

        if (availableQuantity < saleCreateDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough products in stock");
        }

        SaleEntity sale = new SaleEntity();
        sale.setDocumentName(saleCreateDTO.getDocumentName());
        sale.setProduct(product);
        sale.setQuantity(saleCreateDTO.getQuantity());
        sale.setPurchasePrice(saleCreateDTO.getPurchasePrice());

        saleRepository.save(sale);

        return new SaleDTO(sale);

    }
}
