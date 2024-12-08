package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.sale.SaleCreateDTO;
import com.example.romashkastoreapi.dto.sale.SaleDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SaleEntity;
import com.example.romashkastoreapi.repository.ProductRepository;
import com.example.romashkastoreapi.repository.SaleRepository;
import com.example.romashkastoreapi.repository.SupplyRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(
        SaleRepository saleRepository,
        ProductRepository productRepository
    ) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public SaleDTO getSaleById(Long id) {
        return new SaleDTO(findSaleOrThrow(id));
    }

    public SaleEntity findSaleOrThrow(Long id) {
        return saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id " + id));
    }

    public SaleDTO createSale(@Valid SaleCreateDTO saleCreateDTO) {
        ProductEntity product = productRepository.findById(saleCreateDTO.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        checkQuantity(saleCreateDTO, product.getQuantity());

        SaleEntity sale = new SaleEntity();
        sale.setDocumentName(saleCreateDTO.getDocumentName());
        sale.setProduct(product);
        sale.setQuantity(saleCreateDTO.getQuantity());
        sale.setPurchasePrice(calculateSalePrice(saleCreateDTO, product));
        product.setQuantity(product.getQuantity() - saleCreateDTO.getQuantity());

        saleRepository.save(sale);

        return new SaleDTO(sale);
    }

    private BigDecimal calculateSalePrice(SaleCreateDTO saleCreateDTO, ProductEntity product) {
        return product.getPrice().multiply(BigDecimal.valueOf(saleCreateDTO.getQuantity()));
    }

    private static void checkQuantity(SaleCreateDTO saleCreateDTO, int quantity) {
        if (quantity < saleCreateDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough products in stock");
        }
    }


    public SaleDTO updateSale(Long id, @Valid SaleCreateDTO saleCreateDTO) {
        SaleEntity sale = saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id " + id));

        sale.setDocumentName(saleCreateDTO.getDocumentName());
        //product was changed
        if (!sale.getProduct().getId().equals(saleCreateDTO.getProductId())) {
            ProductEntity newProduct = productRepository.findById(saleCreateDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            ProductEntity oldProduct = sale.getProduct();
            checkQuantity(saleCreateDTO, newProduct.getQuantity());
            //revert old sales
            oldProduct.setQuantity(oldProduct.getQuantity() + sale.getQuantity());
            newProduct.setQuantity(newProduct.getQuantity() - saleCreateDTO.getQuantity());
            sale.setProduct(newProduct);
            sale.setPurchasePrice(calculateSalePrice(saleCreateDTO, newProduct));
            sale.setQuantity(saleCreateDTO.getQuantity());
            productRepository.saveAll(List.of(oldProduct, newProduct));
        } else if (sale.getQuantity() != saleCreateDTO.getQuantity()) { // only quantity was changed
            //revert old quantity
            int availableQuantity = sale.getProduct().getQuantity() + sale.getQuantity();
            checkQuantity(saleCreateDTO, availableQuantity);
            sale.setPurchasePrice(calculateSalePrice(saleCreateDTO, sale.getProduct()));
            sale.setQuantity(saleCreateDTO.getQuantity());
            //update product quantity
            sale.getProduct().setQuantity(availableQuantity - saleCreateDTO.getQuantity());
            productRepository.save(sale.getProduct());
        }

        saleRepository.save(sale);
        return new SaleDTO(sale);
    }

    public void deleteSale(Long id) {
        Optional<SaleEntity> optionalSale = saleRepository.findById(id);
        if (optionalSale.isEmpty()) {
            return;
        }
        SaleEntity sale = optionalSale.get();
        sale.getProduct().setQuantity(sale.getProduct().getQuantity() + sale.getQuantity());
        productRepository.save(sale.getProduct());
        saleRepository.deleteById(id);
    }
}
