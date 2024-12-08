package com.example.romashkastoreapi.controller;


import com.example.romashkastoreapi.dto.sale.SaleCreateDTO;
import com.example.romashkastoreapi.dto.sale.SaleDTO;
import com.example.romashkastoreapi.service.SaleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(
        @RequestBody @Valid SaleCreateDTO saleCreateDTO
    ) {
        return ResponseEntity.status(201).body(saleService.createSale(saleCreateDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<SaleDTO> updateSale(
        @PathVariable @Positive Long id,
        @RequestBody @Valid SaleCreateDTO saleCreateDTO
    ) {
        return ResponseEntity.ok(saleService.updateSale(id, saleCreateDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable @Positive Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}

