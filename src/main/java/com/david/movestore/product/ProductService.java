package com.david.movestore.product;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.david.movestore.exceptions.NotNullFileException;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository repository;

  public Product saveProduct(ProductRequest request, BindingResult result) throws NotNullFileException, IOException {
    System.out.println("=> New post request");
    request = uploadFile(request, result);
    Product product = Product.builder()
        .name(request.getName())
        .price(request.getPrice())
        .image(request.getImgUrl())
        .createAt(new Date(System.currentTimeMillis()))
        .quantity(request.getQuantity())
        .lastUpdate(new Date(System.currentTimeMillis()))
        .description(request.getDescription())
        .build();
    System.out.println("=> New product created with name: " + product.getName());
    return repository.save(product);
  }

  public Product updateProduct(ProductRequest request, BindingResult result) throws NotNullFileException, IOException {
    Product product = Product.builder()
        .name(request.getName())
        .price(request.getPrice())
        .quantity(request.getQuantity())
        .lastUpdate(new Date(System.currentTimeMillis()))
        .description(request.getDescription())
        .build();

    if (request.getFile() != null && !request.getFile().isEmpty()) {
      request = uploadFile(request, result);
      product.setImage(request.getImgUrl());
    }

    product.setId(request.getId());
    return repository.save(product);
  }

  @SuppressWarnings("rawtypes")
  public ProductRequest uploadFile(ProductRequest request, BindingResult result)
      throws NotNullFileException, IOException {
    FileValidator validator = new FileValidator();
    validator.validate(request, result);

    Map uploadResult = Singleton.getCloudinary().uploader().upload(request.getFile().getBytes(),
        ObjectUtils.asMap("resource_type", "auto"));
    System.out.println(" => Upload file into " + uploadResult.get("url").toString());
    request.setImgUrl(uploadResult.get("url").toString());
    return request;
  }
}
