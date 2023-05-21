package com.david.movestore.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {
  @Id @GeneratedValue
  private Integer id;
  @NotNull(message = "Product name is required.")
  private String name;
  private String description;
  @NotNull(message = "Product name is required.")
  private String image;
  @NotNull(message = "Product name is required.")
  private Integer quantity;
  @NotNull(message = "Product name is required.")
  private Double price;
}
