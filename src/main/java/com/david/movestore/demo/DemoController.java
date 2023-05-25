package com.david.movestore.demo;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
  private final PhotoService service;

  @GetMapping
  public ResponseEntity<ModelMap> listPhotos(ModelMap model) {
    model.addAttribute("photos", service.getAll());
    return ResponseEntity.ok(model);
  }

  @PostMapping(value = "/upload", consumes = { "multipart/form-data" })
  public ResponseEntity<Photo> savePhoto(
      @ModelAttribute PhotoUpload photoUpload,
      BindingResult result) throws IOException {
    return ResponseEntity.ok(service.uploadFile(photoUpload, result));

  }

}
