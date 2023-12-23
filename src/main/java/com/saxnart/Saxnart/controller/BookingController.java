package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            boolean checkBooked = bookingService.createBooking(bookingDTO);
            if(checkBooked) return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", ""));
                else return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "this seat is not empty", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error creating booking", ""));
                    //new ResponseEntity<>("Error creating booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/book")
//    public ResponseEntity<ResponseObject> bookSeats(@RequestBody BookingRequestDTO booking) {
//        String bookedBooking = bookingService.bookSeats(booking);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseObject("ok", bookedBooking, ""));
//    }
//    @GetMapping("/seats/{bookingId}")
//    public ResponseEntity<ResponseObject> getSeatsByBookingId(@PathVariable Long bookingId) {
//        List<SeatEntity> seats = bookingService.getSeatsByBookingId(bookingId);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseObject("ok", "Seats by bookingID", seats));
//    }
}
