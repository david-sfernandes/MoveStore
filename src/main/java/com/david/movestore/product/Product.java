package com.david.movestore.product;

import java.time.LocalDateTime;
import java.util.Date;

import com.cloudinary.StoredFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue
  private Integer id;
  @NotNull(message = "Product name is required.")
  private String name;
  private String description;
  private String image;
  @NotNull(message = "Product quantity is required.")
  private Integer quantity;
  @NotNull(message = "Product price is required.")
  @Min(value = 0, message = "Price should not be negative.")
  private Double price;
  private Date createAt;
  private Date lastUpdate;

  public StoredFile getUpload() {
    StoredFile file = new StoredFile();
    file.setPreloadedFile(image);
    return file;
  }

  public void setUpload(StoredFile file) {
    this.image = file.getPreloadedFile();
  }
}
