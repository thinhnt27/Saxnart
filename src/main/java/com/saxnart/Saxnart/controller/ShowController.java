package com.saxnart.Saxnart.controller;


import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.ShowService;
import com.saxnart.Saxnart.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
            List<ShowDTO> shows = showService.getAllShow();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", shows));
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
            List<ShowDTO> allShow = showService.findShowsAfterDate(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getFirstShowAfterDateASC")
    public ResponseEntity<ResponseObject> getFirstShowAfterDateASC() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = java.sql.Date.valueOf(currentDate);
            ShowDTO show = showService.findFirstShowsAfterDateASC(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", show));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getByShowDate/{dateString}")
    public ResponseEntity<ResponseObject> getByShowDate(@PathVariable String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Nếu bạn cần sử dụng java.sql.Date
            ShowDTO show = showService.findShowByShowDate(sqlDate);
            if(show == null){
                return ResponseEntity.ok(new ResponseObject("ok", "Success", "Do not have any show in this day"));
            }
            return ResponseEntity.ok(new ResponseObject("ok", "Success", show));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

    @GetMapping("/getShowById/{id}")
    public ResponseEntity<ResponseObject> getShowById(@PathVariable Long id ) {
        try {
            ShowDTO show = showService.getShowById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", show));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }

//    @GetMapping("/getShowsSpecialTrue")
//    public ResponseEntity<ResponseObject> getShowsBySpecialTrue() {
//        try {
//            List<ShowDTO> allShow = showService.getShowDTOHaveSpecialIsTrue();
//            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get seat by show", ""));
//        }
//    }


//    @GetMapping("/getShowsSpecialFalse")
//    public ResponseEntity<ResponseObject> getShowsBySpecialFalse() {
//        try {
//            List<ShowDTO> allShow = showService.getShowDTOHaveSpecialIsFalse();
//            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseObject("error", "Error get seat by show", ""));
//        }
//    }

    @PostMapping("/createShow")
    public ResponseEntity<ResponseObject> createShow(@RequestBody ShowDTO show){
        try {
            String serviceShow = showService.createShow(show);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", serviceShow, ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", e.getMessage(), ""));
        }
    }
    @GetMapping("/getShowsSpecialFalseAndAfterDate")
    public ResponseEntity<ResponseObject> getShowsBySpecialFalseAndAfterDate() {
        try {
            LocalDate currentDate = LocalDate.now();
            Date currentDateSql = java.sql.Date.valueOf(currentDate);
//            List<ShowDTO> allShow = showService.findShowsDTOAfterDateAndSpecialIsFalse(currentDateSql);
            List<ShowEntity> allShow = showService.findShowsAfterDateAndSpecialIsFalse(currentDateSql);
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
//            List<ShowDTO> allShow = showService.findShowsDTOAfterDateAndSpecialIsTrue(currentDateSql);
            List<ShowEntity> allShow = showService.findShowsAfterDateAndSpecialIsTrue(currentDateSql);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", allShow));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get seat by show", ""));
        }
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody ShowDTO show) {
        try {
            if(id.equals(show.getId())){
                ShowEntity showEntity = showService.updateShow(id,show);
                return ResponseEntity.ok(new ResponseObject("ok", "update success", showEntity));
            }else return ResponseEntity.ok(new ResponseObject("ok", "Id không khớp", ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", e.getMessage(), ""));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        try {
            String deleteShow = showService.deleteShowById(id);
            return ResponseEntity.ok(new ResponseObject("ok", deleteShow, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error delete show", ""));
        }
    }

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<ResponseObject> updateStatus(@PathVariable Long id) {
        try {
            String updatedShow = showService.updateStatus(id);
            return ResponseEntity.ok(new ResponseObject("ok", updatedShow, ""));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error update show", ""));
        }
    }

}
