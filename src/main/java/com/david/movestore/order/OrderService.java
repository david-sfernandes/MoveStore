package com.david.movestore.order;

import com.david.movestore.orderProduct.OrderProduct;
import com.david.movestore.orderProduct.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository repository;

  public Order save(OrderRequest request) {
    List<OrderProduct> orderProducts = new ArrayList<>();
    for (OrderProductDto dto : request.getProducts()) {
      orderProducts.add(OrderProduct.builder()
        .quantity(dto.getQuantity())
        .productId(dto.getProductId())
        .build());
    }

    Order order = Order.builder()
      .status(Status.CONFIRMED)
      .orderDate(request.getOrderDate())
      .orderProducts(orderProducts)
      .userEmail(request.getUserEmail())
      .build();
    order = repository.save(order);

    repository.save(order);
    return order;
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
