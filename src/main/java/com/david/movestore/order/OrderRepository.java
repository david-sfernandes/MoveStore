package com.david.movestore.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findByUserEmail(String userEmail);
}
