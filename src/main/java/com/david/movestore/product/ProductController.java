package com.david.movestore.product;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.movestore.exceptions.NotFoundException;
import com.david.movestore.exceptions.NotNullFileException;

import lombok.RequiredArgsConstructor;

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
  public ResponseEntity<Product> getById(@PathVariable Integer id) {
    return ResponseEntity.ok(repository
      .findById(id)
      .orElseThrow(() -> new NotFoundException(Product.class, "id", id.toString()))
    );
  }

  // @Hidden
  @PostMapping(value = "/", consumes = { "multipart/form-data" })
  @PreAuthorize("hasAuthority('admin:create')")
  public ResponseEntity<Product> addProduct(@ModelAttribute ProductRequest request, BindingResult result)
      throws NotNullFileException, IOException {
    return ResponseEntity.ok(service.saveProduct(request, result));
  }

  // @Hidden
  @PutMapping(value = "/", consumes = { "multipart/form-data" })
  @PreAuthorize("hasAuthority('admin:update')")
  public ResponseEntity<Product> updateProduct(@ModelAttribute ProductRequest request, BindingResult result)
      throws NotNullFileException, IOException {
    return ResponseEntity.ok(service.updateProduct(request, result));
  }
}
