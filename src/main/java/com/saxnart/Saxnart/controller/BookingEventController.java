package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.BookingEventEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.BookingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookingevent")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class BookingEventController {

    @Autowired
    private BookingEventService bookingEventService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllBookingEvents() {
        try {
            List<BookingEventEntity> bookingEvents = bookingEventService.getAllContacts();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", bookingEvents));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all booking event", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getFeedbackById(@PathVariable Long id) {
        try {
            BookingEventEntity bookingEvent = bookingEventService.getBookingEventById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", bookingEvent));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get a booking event", ""));
        }
    }

    @PostMapping("/creatBookingEvent")
    public ResponseEntity<ResponseObject> saveFeedback(@RequestBody BookingEventEntity bookingEvent) {
        try {
            BookingEventEntity savedBookingEvent = bookingEventService.saveBookingEvent(bookingEvent);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", savedBookingEvent));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all booking event", ""));
        }
    }

    @PatchMapping("isRead/{bookingEventId}")
    public ResponseEntity<ResponseObject> markContactAsRead(@PathVariable Long bookingEventId) {
        try {
            bookingEventService.updateBookingEvent(bookingEventId);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", bookingEventService.updateBookingEvent(bookingEventId)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all feedback", ""));
        }
    }
}
