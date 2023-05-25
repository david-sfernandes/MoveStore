package com.david.movestore.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.david.movestore.exceptions.NotEnoughStockException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {
  private final OrderService service;

  @PostMapping
  public ResponseEntity<Order> save(@RequestBody OrderRequest request) throws NotEnoughStockException {
    return service.save(request);
  }

  @GetMapping("/{id}")
  public ResponseEntity<List<Order>> getById(@PathVariable Integer id) {
    return ResponseEntity.ok(service.getById(id));
  }

  // @Hidden
  @PutMapping
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<String> updateStatus(@RequestBody UpdateRequest request) {
    return ResponseEntity.ok(service.updateStatus(request));
  }

  // @Hidden
  @GetMapping
  @PreAuthorize("hasAuthority('admin:read')")
  public ResponseEntity<List<Order>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }
}
