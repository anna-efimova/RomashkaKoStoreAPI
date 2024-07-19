package com.example.romashkastoreapi.service;

import com.example.romashkastoreapi.dto.supply.SupplyCreateDTO;
import com.example.romashkastoreapi.dto.supply.SupplyDTO;
import com.example.romashkastoreapi.exception.ResourceNotFoundException;
import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SupplyEntity;
import com.example.romashkastoreapi.repository.ProductRepository;
import com.example.romashkastoreapi.repository.SupplyRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;

    public SupplyService(
        SupplyRepository supplyRepository,
        ProductRepository productRepository
    ) {
        this.supplyRepository = supplyRepository;
        this.productRepository = productRepository;
    }

    public SupplyDTO getSupplyById(Long id) {
        return new SupplyDTO(findSupplyOrThrow(id));
    }

    public SupplyEntity findSupplyOrThrow(Long id) {
        return supplyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supply not found with id " + id));
    }

    public SupplyDTO createSupply(@Valid SupplyCreateDTO supplyCreateDTO) {
        ProductEntity product = productRepository.findById(supplyCreateDTO.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        SupplyEntity supply = new SupplyEntity();
        supply.setDocumentName(supplyCreateDTO.getDocumentName());
        supply.setProduct(product);
        supply.setQuantity(supplyCreateDTO.getQuantity());
        product.setQuantity(product.getQuantity() + supplyCreateDTO.getQuantity());

        productRepository.save(product);
        supplyRepository.save(supply);
        return new SupplyDTO(supply);
    }



    public SupplyDTO updateSupply(Long id, @Valid SupplyCreateDTO supplyCreateDTO) {
        SupplyEntity supply = supplyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Supply not found with id " + id));

        supply.setDocumentName(supplyCreateDTO.getDocumentName());
        //product was changed
        if (!supply.getProduct().getId().equals(supplyCreateDTO.getProductId())) {
            ProductEntity newProduct = productRepository.findById(supplyCreateDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            ProductEntity oldProduct = supply.getProduct();

            oldProduct.setQuantity(oldProduct.getQuantity() - supply.getQuantity());
            newProduct.setQuantity(newProduct.getQuantity() + supplyCreateDTO.getQuantity());
            supply.setProduct(newProduct);
            supply.setQuantity(supply.getQuantity());
            productRepository.saveAll(List.of(oldProduct, newProduct));
        }else if(supply.getQuantity() != supply.getQuantity()) {
            ProductEntity product = supply.getProduct();
            int availableQuantity = product.getQuantity() - supply.getQuantity() + supplyCreateDTO.getQuantity();
            supply.setQuantity(availableQuantity);
            productRepository.save(product);
        }

        supplyRepository.save(supply);
        return new SupplyDTO(supply);
    }

    public void deleteSupply(Long id) {
        Optional<SupplyEntity> optionalSupply = supplyRepository.findById(id);
        if(optionalSupply.isEmpty()) {
            return;
        }
        SupplyEntity sale = optionalSupply.get();
        sale.getProduct().setQuantity(sale.getProduct().getQuantity() - sale.getQuantity());
        productRepository.save(sale.getProduct());
        supplyRepository.deleteById(id);
    }
}
