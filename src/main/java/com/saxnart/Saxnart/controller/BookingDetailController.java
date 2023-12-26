package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookingDetail")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class BookingDetailController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/getAll/{showtimeId}")
    public ResponseEntity<ResponseObject> getBookingDetailsByShow(@PathVariable Long showtimeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", bookingService.getBookingDetailsByShowTime(showtimeId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error, something wrong", ""));
            //new ResponseEntity<>("Error creating booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
