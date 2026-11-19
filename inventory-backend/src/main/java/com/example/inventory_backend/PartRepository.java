package com.example.inventory_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartRepository extends JpaRepository<PartEntity, Long>{
    @Modifying
    @Transactional
    @Query("""
    UPDATE PartEntity p
    SET p.quantity = p.quantity - 1
    WHERE p.id = :id AND p.quantity > 0
    """)
    int useOne(@Param("id") Long id);
}