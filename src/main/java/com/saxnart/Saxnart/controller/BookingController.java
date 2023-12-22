package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.request.BookingRequestDTO;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @PostMapping("/book")
    public ResponseEntity<ResponseObject> bookSeats(@RequestBody BookingRequestDTO booking) {
        String bookedBooking = bookingService.bookSeats(booking);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", bookedBooking, ""));
    }
    @GetMapping("/seats/{bookingId}")
    public ResponseEntity<ResponseObject> getSeatsByBookingId(@PathVariable Long bookingId) {
        List<SeatEntity> seats = bookingService.getSeatsByBookingId(bookingId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Seats by bookingID", seats));
    }
}
