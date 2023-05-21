package com.david.movestore.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductRepository repository;
  private final ProductService service;

  @GetMapping("/")
  public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Product>> getById(
    @PathVariable Integer id
  ) {
    return ResponseEntity.ok(repository.findById(id));
  }

//  @Hidden
  @PostMapping
  @PreAuthorize("hasAuthority('admin:create')")
  public ResponseEntity<Product> addProduct(
    @RequestBody ProductRequest request
  ) {
    return ResponseEntity.ok(service.saveProduct(request));
  }

//  @Hidden
  @PutMapping
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest request) {
    return ResponseEntity.ok(service.updateProduct(request));
  }
}
