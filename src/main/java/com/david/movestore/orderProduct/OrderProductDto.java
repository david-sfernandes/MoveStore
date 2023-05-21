package com.david.movestore.orderProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto implements Serializable {
  private Integer productId;
  private Integer quantity;
}
