package com.example.romashkastoreapi.repository;

import com.example.romashkastoreapi.dto.product.ProductDTO;
import com.example.romashkastoreapi.model.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    private final ConcurrentHashMap<Long, ProductDTO> products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public List<ProductDTO> findAll() {
        return products.values().stream().collect(Collectors.toList());
    }

    public Optional<ProductDTO> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public ProductDTO save(ProductDTO product) {
        if (product.getId() == null) {
            product.setId(counter.incrementAndGet());
        }
        products.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        products.remove(id);
    }

}
