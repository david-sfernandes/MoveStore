package com.david.movestore.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  @Override
  Optional<Product> findById(Integer id);
}
