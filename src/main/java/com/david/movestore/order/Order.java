package com.david.movestore.order;

import com.david.movestore.orderProduct.OrderProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue
  private Integer id;
  @NotNull(message = "Order should have at least 1 product.")
  @OneToMany(cascade = CascadeType.ALL)
  public List<OrderProduct> orderProducts;
  private String userEmail;
  private Date orderDate;
  private Date lastUpdate;
  @Enumerated(EnumType.STRING)
  private Status status;
}
