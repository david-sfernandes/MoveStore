package com.david.movestore.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public Product saveProduct(ProductRequest request) {
    var product = getProduct(request);
    return productRepository.save(product);
  }

  public Product getProduct(ProductRequest request) {
    return Product.builder()
      .price(request.getPrice())
      .description(request.getDescription())
      .image(request.getImage())
      .name(request.getName())
      .quantity(request.getQuantity())
      .build();
  }

  public Product updateProduct(ProductRequest request) {
    var product = getProduct(request);
    product.setId(request.getId());
    return productRepository.save(product);
  }
}
