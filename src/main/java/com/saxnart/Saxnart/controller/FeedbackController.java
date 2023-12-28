package com.saxnart.Saxnart.controller;

import com.saxnart.Saxnart.entity.FeedbackEntity;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedbacks")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping
    public ResponseEntity<ResponseObject> getAllFeedbacks() {
        try {
            List<FeedbackEntity> feedbacks = feedbackService.getAllFeedbacks();
            return ResponseEntity.ok(new ResponseObject("ok", "Success", feedbacks));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all feedback", ""));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getFeedbackById(@PathVariable Long id) {
        try {
            FeedbackEntity feedback = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", feedback));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get a feedback", ""));
        }
    }

    @PostMapping("/creatFeedback")
    public ResponseEntity<ResponseObject> saveFeedback(@RequestBody FeedbackEntity feedback) {
        try {
            FeedbackEntity savedFeedback = feedbackService.saveFeedback(feedback);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", savedFeedback));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all feedback", ""));
        }
    }

    @PatchMapping("isRead/{feedbackId}")
    public ResponseEntity<ResponseObject> markFeedbackAsRead(@PathVariable Long feedbackId) {
        try {
            feedbackService.updateFeedback(feedbackId);
            return ResponseEntity.ok(new ResponseObject("ok", "Success", feedbackService.updateFeedback(feedbackId)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("error", "Error get all feedback", ""));
        }
    }
}
