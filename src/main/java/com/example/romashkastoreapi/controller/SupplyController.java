package com.example.romashkastoreapi.controller;


import com.example.romashkastoreapi.dto.supply.SupplyCreateDTO;
import com.example.romashkastoreapi.dto.supply.SupplyDTO;
import com.example.romashkastoreapi.service.SupplyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    @Autowired
    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<SupplyDTO> getSupply(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(supplyService.getSupplyById(id));
    }

    @PostMapping
    public ResponseEntity<SupplyDTO> createSupply(
        @RequestBody @Valid SupplyCreateDTO supplyCreateDTO
    ) {
        return ResponseEntity.status(201).body(supplyService.createSupply(supplyCreateDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<SupplyDTO> updateSupply(
        @PathVariable @Positive Long id,
        @RequestBody @Valid SupplyCreateDTO saleCreateDTO
    ) {
        return ResponseEntity.ok(supplyService.updateSupply(id, saleCreateDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable @Positive Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.noContent().build();
    }
}

