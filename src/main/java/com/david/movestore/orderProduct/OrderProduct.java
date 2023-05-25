package com.david.movestore.orderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_products")
public class OrderProduct {
  @Id
  @GeneratedValue
  private Integer id;
  private Integer productId;
  private Integer quantity;
  private Double price;
  private String name;
  private String image;
}
