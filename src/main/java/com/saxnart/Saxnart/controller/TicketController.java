package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class TicketController {

    @Autowired
    private TicketTypeService ticketTypeService;
    @GetMapping("/getAllTicket")
    public ResponseEntity<ResponseObject> getAllSeats() {
        try {
            List<TicketDTO> allSeats = ticketTypeService.getAllTicket();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allSeats));
        } catch (Exception e) {
            e.printStackTrace(); // In ra console để xem lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }
}
