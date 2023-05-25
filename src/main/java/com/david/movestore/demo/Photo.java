package com.david.movestore.demo;

import java.util.Date;

import com.cloudinary.StoredFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
  @Id @GeneratedValue
  public Integer id;
  public String title;
  public String image;
  public Date createAt;

  public StoredFile getUpload() {
    StoredFile file = new StoredFile();
    file.setPreloadedFile(image);
    return file;
  }

  public void setUpload(StoredFile file) {
    this.image = file.getPreloadedFile();
  }
}
