package com.tpop.zaikokanri.master.repository;

import com.tpop.zaikokanri.master.entities.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Page<Warehouse> findByWarehouseCdContainingAndWarehouseNameContaining(String warehouseCd ,String warehouseName,Pageable pageable);

    Optional<Warehouse> findWarehouseByWarehouseCd(String warehouseCode);
}