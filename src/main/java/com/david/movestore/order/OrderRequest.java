package com.david.movestore.order;

import com.david.movestore.orderProduct.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  private List<OrderProductDto> products;
  private Date orderDate;
}

