package com.example.romashkastoreapi.repository;


import com.example.romashkastoreapi.model.SupplyEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SupplyRepository extends JpaRepository<SupplyEntity, Long> {
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM SupplyEntity s WHERE s.product.id = :productId")
    int findTotalSuppliedQuantityByProductId(@Param("productId") Long productId);


}


