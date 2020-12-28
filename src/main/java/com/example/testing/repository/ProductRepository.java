package com.example.testing.repository;
import com.example.testing.entity.Dealer;
import com.example.testing.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByDealersIn(List<Dealer> dealers, Pageable pageable);
}