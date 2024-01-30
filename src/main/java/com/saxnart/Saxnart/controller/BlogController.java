package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.BlogEntity;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class BlogController {

    @Autowired
    private BlogService blogService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllBlog() {
        try {
            List<BlogEntity> blogEntities = blogService.getAllBlog();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", blogEntities));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all blog", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBlogById(@PathVariable Long id) {
        try {
            BlogEntity blogEntity = blogService.getBlogById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", blogEntity));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get blog", ""));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBlog(@RequestBody BlogEntity blogEntity) {
        try {
            BlogEntity saveBlog = blogService.saveBlog(blogEntity);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", saveBlog));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error create blog", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBlog(@PathVariable Long id) {
        try {
            String deleteBlogEntity = blogService.deleteBlog(id);
            return ResponseEntity.ok(new ResponseObject("ok", deleteBlogEntity, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete blog", ""));
        }
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id) {
        try {
            String updatedBlog = blogService.updateStatus(id);
            return ResponseEntity.ok(new ResponseObject("ok", updatedBlog, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update blog", ""));
        }
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody BlogEntity blogEntity) {
        try {
            if(id.equals(blogEntity.getId())){
                BlogEntity updatedBlog = blogService.update(id,blogEntity);
                return ResponseEntity.ok(new ResponseObject("ok", "update success", updatedBlog));
            }else return ResponseEntity.ok(new ResponseObject("ok", "Id không khớp", ""));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update blog", ""));
        }
    }

}
