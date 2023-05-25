package com.david.movestore.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.david.movestore.exceptions.NotNullFileException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductRepository repository;
  private final ProductService service;

  @GetMapping("/")
  public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Product>> getById(
      @PathVariable Integer id) {
    return ResponseEntity.ok(repository.findById(id));
  }

  // @Hidden
  @PostMapping(value = "/", consumes = { "multipart/form-data" })
  @PreAuthorize("hasAuthority('admin:create')")
  public ResponseEntity<Product> addProduct(@RequestBody ProductRequest request, BindingResult result)
      throws NotNullFileException, IOException {
    return ResponseEntity.ok(service.saveProduct(request, result));
  }

  // @Hidden
  @PutMapping(value = "/", consumes = { "multipart/form-data" })
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest request, BindingResult result)
      throws NotNullFileException, IOException {
    return ResponseEntity.ok(service.updateProduct(request, result));
  }
}
