package com.david.movestore.order;

import com.david.movestore.orderProduct.OrderProduct;
import jakarta.persistence.*;
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
  @Id @GeneratedValue
  private Integer id;
  @OneToMany(cascade = CascadeType.ALL)
  public List<OrderProduct> orderProducts;
//  @ManyToOne
  private String userEmail;
  private Date orderDate;
  @Enumerated(EnumType.STRING)
  private Status status;
}
