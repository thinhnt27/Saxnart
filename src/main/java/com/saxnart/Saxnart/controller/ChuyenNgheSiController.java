package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.ChuyenNgheSiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chuyen-nghe-si")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class ChuyenNgheSiController {
    @Autowired
    private ChuyenNgheSiService chuyenNgheSiService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllChuyenNgheSi() {
        try {
            List<ChuyenNgheSiEntity> chuyenNgheSiEntities = chuyenNgheSiService.getAllChuyenNgheSi();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", chuyenNgheSiEntities));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all chuyen nghe si", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getChuyenNgheSiById(@PathVariable Long id) {
        try {
            ChuyenNgheSiEntity chuyenNgheSiEntity = chuyenNgheSiService.getChuyenNgheSiById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", chuyenNgheSiEntity));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get chuyen nghe si", ""));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createChuyenNgheSi(@RequestBody ChuyenNgheSiEntity chuyenNgheSiEntity) {
        try {
            ChuyenNgheSiEntity savedChuyenNgheSiEntity = chuyenNgheSiService.saveChuyenNgheSi(chuyenNgheSiEntity);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", savedChuyenNgheSiEntity));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error create chuyen nghe si", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteChuyenNgheSi(@PathVariable Long id) {
        try {
            String deleteChuyenNgheSiEntity = chuyenNgheSiService.deleteChuyenNgheSi(id);
            return ResponseEntity.ok(new ResponseObject("ok", deleteChuyenNgheSiEntity, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete chuyen nghe si", ""));
        }
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id) {
        try {
            String updatedChuyenNgheSi = chuyenNgheSiService.updateStatus(id);
            return ResponseEntity.ok(new ResponseObject("ok", updatedChuyenNgheSi, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update chuyen nghe si", ""));
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updatep(@PathVariable Long id, @RequestBody ChuyenNgheSiEntity chuyenNgheSiEntity) {
        try {
            if(id.equals(chuyenNgheSiEntity.getId())){
                ChuyenNgheSiEntity updatedChuyenNgheSi = chuyenNgheSiService.update(id,chuyenNgheSiEntity);
                return ResponseEntity.ok(new ResponseObject("ok", "update success", updatedChuyenNgheSi));
            }else return ResponseEntity.ok(new ResponseObject("ok", "Id không khớp", ""));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update chuyen nghe si", ""));
        }
    }
}
