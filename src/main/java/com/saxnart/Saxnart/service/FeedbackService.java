package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.controller.FeedbackController;
import com.saxnart.Saxnart.entity.FeedbackEntity;
import com.saxnart.Saxnart.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<FeedbackEntity> getAllFeedbacks(){
        return feedbackRepository.findAll();
    }

    public FeedbackEntity getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found with id: " + id));
    }

    public FeedbackEntity saveFeedback(FeedbackEntity feedback) {
        return feedbackRepository.save(feedback);
    }

    public FeedbackEntity updateFeedback(Long feedbackId){
        Optional<FeedbackEntity> feedbackOptional = feedbackRepository.findById(feedbackId);
        if (feedbackOptional.isPresent()) {
            FeedbackEntity feedback = feedbackOptional.get();
            feedback.setStatus(true);
            feedbackRepository.save(feedback);
            return feedback;
        }else return feedbackOptional.get();
    }
}
