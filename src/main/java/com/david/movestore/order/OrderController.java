package com.david.movestore.order;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.movestore.exceptions.NotEnoughStockException;
import com.david.movestore.exceptions.NotFoundException;
import com.david.movestore.user.Role;
import com.david.movestore.user.User;
import com.david.movestore.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
    User user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new NotFoundException(User.class, "email", userEmail));

    if (user.getRole() == Role.USER) {
      return ResponseEntity.ok(service.getByEmail(userEmail));
    }
    return ResponseEntity.ok(service.getAll());
  }
}
