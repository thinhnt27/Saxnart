package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.ImageHomeEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.repository.ImageHomeRopository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/imagehome")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class ImageHomeController {

    @Autowired
    private ImageHomeRopository imageHomeRopository;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllImageHome() {
        try {
            List<ImageHomeEntity> imageHomeEntities = imageHomeRopository.findAll();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", imageHomeEntities));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all image home", ""));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseObject> update(@RequestBody ImageHomeEntity imageHomeEntity) {
        try {
            Optional<ImageHomeEntity> imageHome = imageHomeRopository.findById(imageHomeEntity.getId());
            if(imageHome.isPresent()){
                imageHome.get().setImage(imageHomeEntity.getImage());
                imageHome.get().setTitle(imageHomeEntity.getTitle());
                imageHome.get().setContent(imageHomeEntity.getContent());
                imageHome.get().setDateModified(new Date());
                imageHome.get().setAuthorModified(imageHomeEntity.getAuthorModified());
                imageHomeRopository.save(imageHome.get());
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update image home", ""));
        }
    }
}
