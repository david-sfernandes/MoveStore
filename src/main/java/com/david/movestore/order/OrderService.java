package com.david.movestore.order;

import com.david.movestore.exceptions.NotFoundException;
import com.david.movestore.orderProduct.OrderProduct;
import com.david.movestore.orderProduct.OrderProductDto;
import com.david.movestore.product.Product;
import com.david.movestore.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository repository;
  private final ProductRepository productRepository;

  public ResponseEntity<Order> save(OrderRequest request) {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    List<OrderProduct> orderProducts = new ArrayList<>();
    for (OrderProductDto dto : request.getProducts()) {
      Product product = productRepository
        .findById(dto.getProductId())
        .orElseThrow(() -> new NotFoundException(Product.class, "id", dto.getProductId().toString()));

      if (product.getQuantity() < dto.getQuantity()) {
        ResponseEntity.badRequest().body("Not enough items on stock.");
      }
      orderProducts.add(OrderProduct.builder()
        .quantity(dto.getQuantity())
        .productId(dto.getProductId())
        .build());
    }

    Order order = Order.builder()
      .status(Status.CONFIRMED)
      .orderDate(request.getOrderDate())
      .orderProducts(orderProducts)
      .userEmail(userEmail)
      .build();
    return ResponseEntity.ok(repository.save(order));
  }

  public String updateStatus(UpdateRequest request) {
    repository
      .findById(request.orderId)
      .orElseThrow()
      .setStatus(request.status);
    return "Status is up to date.";
  }

  public List<Order> getAll() {
    return repository.findAll();
  }

  public List<Order> getById(Integer id) {
    return null;
  }
}
