package com.david.movestore.demo;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PhotoService {
  private final PhotoRepository photoRepository;

  @SuppressWarnings("rawtypes")
  public Photo uploadFile(
      PhotoUpload photoUpload,
      BindingResult result) throws IOException {
    PhotoUploadValidator validator = new PhotoUploadValidator();
    validator.validate(photoUpload, result);

    Map uploadResult;

    if (photoUpload.getFile() == null && photoUpload.getFile().isEmpty()) {
      return new Photo();
    }

    System.out.println("Not null");
    uploadResult = Singleton.getCloudinary().uploader().upload(photoUpload.getFile().getBytes(),
        ObjectUtils.asMap("resource_type", "auto"));

    System.out.println("- uploadRes: " + uploadResult);
    photoUpload.setPublicId(uploadResult.get("public_id").toString());
    Object version = uploadResult.get("version");
    photoUpload.setVersion(((Number) version).longValue());

    photoUpload.setSignature(uploadResult.get("signature").toString());
    photoUpload.setFormat(uploadResult.get("format").toString());
    photoUpload.setResourceType(uploadResult.get("resource_type").toString());

    Photo photo = new Photo();
    photo.setTitle(photoUpload.getTitle());
    photo.setImage(uploadResult.get("url").toString());
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    try {
      photo.setCreateAt(dateFormat.parse(uploadResult.get("created_at").toString()));
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    photoRepository.save(photo);
    return photo;
  }

  public List<Photo> getAll() {
    return photoRepository.findAll();
  }
}
