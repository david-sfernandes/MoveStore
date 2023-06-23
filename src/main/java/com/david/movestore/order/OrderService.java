package com.david.movestore.order;

import java.util.ArrayList;
import java.util.List;

import org.cloudinary.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.david.movestore.SimpleResponse.SimpleResponse;
import com.david.movestore.exceptions.NotEnoughStockException;
import com.david.movestore.exceptions.NotFoundException;
import com.david.movestore.orderProduct.OrderProduct;
import com.david.movestore.orderProduct.OrderProductDto;
import com.david.movestore.product.Product;
import com.david.movestore.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository repository;
  private final ProductRepository productRepository;

  public ResponseEntity<Order> save(OrderRequest request) throws NotEnoughStockException {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    List<OrderProduct> orderProducts = new ArrayList<>();
    for (OrderProductDto dto : request.getProducts()) {
      Product product = productRepository
          .findById(dto.getProductId())
          .orElseThrow(() -> new NotFoundException(Product.class, "id", dto.getProductId().toString()));

      if (product.getQuantity() < dto.getQuantity())
        throw new NotEnoughStockException(product.getId(), product.getQuantity());

      product.setQuantity(product.getQuantity() - dto.getQuantity());
      productRepository.save(product);

      orderProducts.add(OrderProduct.builder()
          .productId(dto.getProductId())
          .quantity(dto.getQuantity())
          .price(product.getPrice())
          .image(product.getImage())
          .name(product.getName())
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

  public SimpleResponse updateStatus(UpdateRequest request) {
    Order order = repository
        .findById(request.orderId)
        .orElseThrow(() -> new NotFoundException(Order.class, "id", request.orderId.toString()));
    order.setStatus(request.status);
    repository.save(order);
    return SimpleResponse.builder().message("Status is up to date.").build();
  }

  public List<Order> getAll() {
    return repository.findAll();
  }

  public List<Order> getById(Integer id) {
    return null;
  }

  public List<Order> getByEmail(String userEmail) {
    return repository.findByUserEmail(userEmail);
  }
}
