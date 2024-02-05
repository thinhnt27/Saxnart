package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ticket")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class TicketController {

    @Autowired
    private TicketTypeService ticketTypeService;
//    @GetMapping("/getAllTicket")
//    public ResponseEntity<ResponseObject> getAllSeats() {
//        try {
//            List<TicketDTO> allSeats = ticketTypeService.getAllTicket();
//            return ResponseEntity.ok(new ResponseObject("ok", "Success", allSeats));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get all tickets", ""));
//        }
//    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTicketTypeById(@PathVariable Long id) {
        try{
            TicketTypeEntity ticketType =  ticketTypeService.getTicketTypeById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ticketType));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get ticket", ""));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createTicketType(@RequestBody TicketTypeEntity ticketType) {
        try{
         TicketTypeEntity ticketTypeEntity = ticketTypeService.createTicketType(ticketType);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", ticketTypeEntity));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error post ticket", ""));
        }
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<ResponseObject> updateTicketType(@PathVariable Long id, @RequestBody TicketTypeEntity updatedTicketType) {
//        try{
//            if(id.equals(updatedTicketType.getId())){
//                TicketTypeEntity ticketTypeEntity = ticketTypeService.updateTicketType(id, updatedTicketType);
//                return ResponseEntity.ok(new ResponseObject("ok", "update success", ticketTypeEntity));
//            }else return ResponseEntity.ok(new ResponseObject("ok", "Id không khớp", ""));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get ticket", ""));
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTicketType(@PathVariable Long id) {
        try{
            String ticketType = ticketTypeService.deleteTicketType(id);
            return ResponseEntity.ok(new ResponseObject("ok", ticketType, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get ticket", ""));
        }
    }

}
