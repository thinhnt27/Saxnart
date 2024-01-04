package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.BookingDTO;
import com.saxnart.Saxnart.entity.BookingEntity;
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
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
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
        }
    }

    @GetMapping("/getAllBooking")
    public ResponseEntity<ResponseObject> getAllBooking(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", bookingService.getAllBooking()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error get all booking", ""));
        }
    }

    @GetMapping("/getAllBooking/{showtimeId}")
    public ResponseEntity<ResponseObject> getAllBookingByShow(@PathVariable Long showtimeId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", bookingService.getAllBookingByShow(showtimeId)));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error get all booking", ""));
        }
    }
    @GetMapping("/getAllBookingSeatNum/{showtimeId}")
    public ResponseEntity<ResponseObject> getAllBookingSeatNum(@PathVariable Long showtimeId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", bookingService.getAllBookingSeatNumByShow(showtimeId)));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error get all booking", ""));
        }
    }

    @GetMapping("/CheckBookingSeatNum/{showtimeId}")
    public ResponseEntity<ResponseObject> CheckBookingSeatNum(@PathVariable Long showtimeId, @RequestBody List<String> seatNum){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", bookingService.checkSeatStatusInShow(showtimeId, seatNum)));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error get all booking", ""));
        }
    }

    @PatchMapping("cancel")
    public ResponseEntity<ResponseObject> bookingCancel(@RequestBody BookingDTO bookingEntity) {
        try {
            bookingService.updateStatusBooking(bookingEntity);
            return ResponseEntity.ok(new ResponseObject("ok", bookingService.updateStatusBooking(bookingEntity), ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all booking", ""));
        }
    }

    @PatchMapping("checkBooking")
    public ResponseEntity<ResponseObject> checkBookingOver15minute(@RequestBody BookingDTO bookingEntity) {
        try {
            bookingService.checkBookingOver15minute(bookingEntity);
            return ResponseEntity.ok(new ResponseObject("ok", bookingService.checkBookingOver15minute(bookingEntity), ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all booking", ""));
        }
    }
}
