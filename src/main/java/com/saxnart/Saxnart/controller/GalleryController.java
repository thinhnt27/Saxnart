package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.GalleryEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllGallery() {
        try {
            List<GalleryEntity> galleryEntities = galleryService.getAllGallery();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", galleryEntities));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all gallery", ""));
        }
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createGallery(@RequestBody GalleryEntity galleryEntity) {
        try {
            GalleryEntity savedGallery = galleryService.saveGallery(galleryEntity);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", savedGallery));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error create gallery", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteGallery(@PathVariable Long id) {
        try {
            String deleteGallery = galleryService.deleteGallery(id);
            return ResponseEntity.ok(new ResponseObject("ok", deleteGallery, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete chuyen nghe si", ""));
        }
    }
}
