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
@Builder
@Entity
@Table(name = "order_products")
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
  @Id @GeneratedValue
  private Integer id;
  private Integer productId;
  private Integer quantity;
}
