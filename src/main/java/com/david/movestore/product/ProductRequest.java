package com.david.movestore.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
  Integer id;
  String name;
  String description;
  String image;
  Integer quantity;
  Double price;
}
