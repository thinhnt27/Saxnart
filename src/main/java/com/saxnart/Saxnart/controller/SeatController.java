package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.response.SeatResponse;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.repository.SeatRepository;
import com.saxnart.Saxnart.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/seat")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class SeatController {

    @Autowired
    private SeatService seatService;


    @Autowired
    private SeatRepository seatRepository;


    @GetMapping("/seatByShowtimeId/{showtimeId}")
    public ResponseEntity<ResponseObject> getBookedSeatsByShowtime(@PathVariable Long showtimeId) {
        try {
            List<SeatEntity> bookedSeats = seatService.findBookedSeatsByShowtimeId(showtimeId);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", bookedSeats));
        } catch (Exception e) {
            e.printStackTrace(); // In ra console để xem lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllSeats() {
        try {
            List<SeatEntity> allSeats = seatRepository.findAll();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allSeats));
        } catch (Exception e) {
            e.printStackTrace(); // In ra console để xem lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }
    @GetMapping("/status/{showtimeId}")
    public ResponseEntity<ResponseObject> getSeatStatusByShowtime(@PathVariable Long showtimeId) {
        try {
            List<SeatResponse> seatStatusList = seatService.findSeatsStatusByShow(showtimeId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", seatStatusList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error getting seat status by showtime", ""));
        }
    }
}
