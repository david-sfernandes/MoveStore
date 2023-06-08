package com.david.movestore.order;

import com.david.movestore.exceptions.NotFoundException;
import com.david.movestore.user.Role;
import com.david.movestore.user.User;
import com.david.movestore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.david.movestore.exceptions.NotEnoughStockException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {
  private final OrderService service;
  private final UserRepository userRepository;

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
  public ResponseEntity<List<Order>> getAll() {
    String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException(User.class, "email", userEmail));

    if (user.getRole() == Role.USER) {
      return ResponseEntity.ok(service.getByEmail(userEmail));
    }
    return ResponseEntity.ok(service.getAll());
  }
}
