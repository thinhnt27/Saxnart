package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.IpEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.IpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ip")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class IpController {

    @Autowired
    private IpService ipService;

    // Get all IPs
    @GetMapping
    public ResponseEntity<ResponseObject> getAllIps() {
        try {
            List<IpEntity> ips = ipService.getAllIps();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ips));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all api", ""));
        }
    }

    // Get IP by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getIpById(@PathVariable("id") Long id) {
        try {
            IpEntity ip = ipService.getIpById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ip));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get ip", ""));
        }
    }

    // Create a new IP
    @PostMapping
    public ResponseEntity<ResponseObject> createIp(@RequestBody IpEntity ipEntity) {
        try {
            IpEntity createdIp = ipService.createIp(ipEntity);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", createdIp));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error create ip", ""));
        }
    }


    // Delete an existing IP
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteIp(@PathVariable("id") Long id) {
        try {
            ipService.deleteIp(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete ip", ""));
        }
    }
}
