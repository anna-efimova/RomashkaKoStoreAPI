package com.example.romashkastoreapi.repository;


import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SaleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM SaleEntity s WHERE s.product.id = :productId")
    int findTotalSoldQuantityByProductId(@Param("productId") Long productId);

}


