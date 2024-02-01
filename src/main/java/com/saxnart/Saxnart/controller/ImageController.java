//package com.saxnart.Saxnart.controller;
//
//import com.saxnart.Saxnart.entity.ImageEntity;
//import com.saxnart.Saxnart.model.ResponseObject;
//import com.saxnart.Saxnart.service.ImageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/image")
//@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
//public class ImageController {
//    private final ImageService imageService;
//
//    // Lấy danh sách ảnh
//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getAllImage() {
//        try {
//            List<ImageEntity> allImageEntity = imageService.getAllImage();
//            return ResponseEntity.ok(new ResponseObject("ok", "Success", allImageEntity));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get all image", ""));
//        }
//
//    }
//
//    // Xem ảnh
//    @GetMapping("{id}")
//    public ResponseEntity<?> readImage(@PathVariable Long id) {
//        try {
//            ImageEntity imageEntity = imageService.getImage(id);
//            return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageEntity.getType())).body(imageEntity.getImage());
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get image", ""));
//        }
//    }
//
//    // Upload ảnh
//    @PostMapping("")
//    public ResponseEntity<?> uploadImage(@ModelAttribute("file") MultipartFile file) {
//        try {
//            ImageEntity imageEntity = imageService.uploadImage(file);
//            return ResponseEntity.ok(new ResponseObject("ok", "Success", imageEntity));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error post image", ""));
//        }
//    }
//
//    // Download ảnh
//    @GetMapping("/download/{id}")
//    public ResponseEntity<?> downloadImage(@PathVariable Long id) {
//        ImageEntity imageEntity = imageService.getImage(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(imageEntity.getType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageEntity.getName() + "\"")
//                .body(new ByteArrayResource(imageEntity.getImage()));
//    }
//
//    // Xóa ảnh
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
//        try {
//            imageService.deleteImage(id);
//            return ResponseEntity.ok(new ResponseObject("ok", "delete success", ""));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error delete image", ""));
//        }
//    }
//
//    @PatchMapping("/updateStatus/{id}")
//    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id) {
//        try {
//            String updatedGallery = imageService.updateStatus(id);
//            return ResponseEntity.ok(new ResponseObject("ok", updatedGallery, ""));
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error update image", ""));
//        }
//    }
//}
