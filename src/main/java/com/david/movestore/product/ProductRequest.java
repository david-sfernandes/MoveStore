package com.david.movestore.product;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Singleton;
import com.cloudinary.StoredFile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductRequest extends StoredFile {
  public Integer id;
  public String name;
  public String description;
  public Integer quantity;
  public Double price;
  public MultipartFile file;
  @JsonIgnore
  public String imgUrl;

  public String getUrl() {
    if (version != null && format != null && publicId != null) {
      return Singleton.getCloudinary().url()
          .resourceType(resourceType)
          .type(type)
          .format(format)
          .version(version)
          .generate(publicId);
    } else
      return null;
  }

  public String getComputedSignature() {
    return getComputedSignature(Singleton.getCloudinary());
  }

  public boolean validSignature() {
    return getComputedSignature().equals(signature);
  }
}
