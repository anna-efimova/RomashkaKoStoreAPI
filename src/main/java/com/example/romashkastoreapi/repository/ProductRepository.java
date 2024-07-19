package com.example.romashkastoreapi.repository;


import com.example.romashkastoreapi.model.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select p from ProductEntity p where (:name is null or p.name like %:name%) " +
        "and (:priceFrom is null or p.price >= :priceFrom ) " +
        "and (:priceTo is null or p.price <= :priceTo) " +
        "and (:inStock is null or p.quantity > 0) "
    )
    List<ProductEntity> getFilteredProducts(
        String name, BigDecimal priceFrom, BigDecimal priceTo,
        Boolean inStock, Pageable pageable
    );
}


