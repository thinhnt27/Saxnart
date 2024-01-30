package com.saxnart.Saxnart.controller;


import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.ShowService;
import com.saxnart.Saxnart.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/show")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class ShowController {

    @Autowired
    private ShowService showService;
    @GetMapping("/getAllShow")
    public ResponseEntity<ResponseObject> getAllShows() {
        try {
            List<ShowDTO> allSeats = showService.getAllShowDTO();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allSeats));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getShowsSpecialTrue")
    public ResponseEntity<ResponseObject> getShowsBySpecialTrue() {
        try {
            List<ShowDTO> allShow = showService.getShowDTOHaveSpecialIsTrue();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }


    @GetMapping("/getShowsSpecialFalse")
    public ResponseEntity<ResponseObject> getShowsBySpecialFalse() {
        try {
            List<ShowDTO> allShow = showService.getShowDTOHaveSpecialIsFalse();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @PostMapping("creatShow")
    public ResponseEntity<ResponseObject> creatShow(@RequestBody ShowEntity show){
        try {
            showService.creatShow(show);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Success", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Error creating show", ""));
        }
    }
    @GetMapping("/getShowsSpecialFalseAndAfterDate")
    public ResponseEntity<ResponseObject> getShowsBySpecialFalseAndAfterDate() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = java.sql.Date.valueOf(currentDate);
            List<ShowDTO> allShow = showService.findShowsDTOAfterDateAndSpecialIsFalse(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getShowsSpecialTrueAndAfterDate")
    public ResponseEntity<ResponseObject> getShowsBySpecialTrueAndAfterDate() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = java.sql.Date.valueOf(currentDate);
            List<ShowDTO> allShow = showService.findShowsDTOAfterDateAndSpecialIsTrue(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }
    @GetMapping("/getShowsAfterDate")
    public ResponseEntity<ResponseObject> getShowsByAfterDate() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = java.sql.Date.valueOf(currentDate);
            List<ShowDTO> allShow = showService.findShowsDTOAfterDate(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }


}
