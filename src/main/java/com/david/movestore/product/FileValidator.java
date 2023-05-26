package com.david.movestore.product;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FileValidator implements Validator {
  public boolean supports(Class clazz) {
    return ProductRequest.class.equals(clazz);
  }

  public void validate(Object obj, Errors e) {
    ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
    ProductRequest p = (ProductRequest) obj;
    if (p.getFile() == null || p.getFile().isEmpty()) {
      if (!p.validSignature()) {
        e.rejectValue("signature", "signature.mismatch");
      }
    }
  }
}