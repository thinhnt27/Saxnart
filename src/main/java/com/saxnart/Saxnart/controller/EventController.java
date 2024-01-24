package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.BlogEntity;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.EventEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.EventService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/event")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllEvent() {
        try {
            List<EventEntity> eventEntities = eventService.getAllEvent();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", eventEntities));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all event", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEventById(@PathVariable Long id) {
        try {
            EventEntity eventEntity = eventService.getEventById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", eventEntity));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get event", ""));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createEvent(@RequestBody EventEntity eventEntity) {
        try {
            EventEntity saveEvent = eventService.saveEvent(eventEntity);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", saveEvent));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error create chuyen nghe si", ""));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteEvent(@PathVariable Long id) {
        try {
            String deleteEvent = eventService.deleteEvent(id);
            return ResponseEntity.ok(new ResponseObject("ok", deleteEvent, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete event", ""));
        }
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id) {
        try {
            String updatedEvent = eventService.updateStatus(id);
            return ResponseEntity.ok(new ResponseObject("ok", updatedEvent, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update status event", ""));
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody EventEntity eventEntity) {
        try {
            if(id.equals(eventEntity.getId())){
                EventEntity updatedEvent = eventService.update(id,eventEntity);
                return ResponseEntity.ok(new ResponseObject("ok", "update success", updatedEvent));
            }else return ResponseEntity.ok(new ResponseObject("ok", "Id không khớp", ""));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update event", ""));
        }
    }
}
