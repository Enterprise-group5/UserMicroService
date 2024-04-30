package bt.edu.gcit.usermicroservice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bt.edu.gcit.usermicroservice.entity.Feedback;
import bt.edu.gcit.usermicroservice.entity.User;
import bt.edu.gcit.usermicroservice.service.FeedbackService;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class FeedbackRestController {
    private FeedbackService feedbackService;

    @Autowired
    public FeedbackRestController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public Feedback save(@RequestBody Feedback feedback) {
        return feedbackService.save(feedback);
    }

    @GetMapping("/allfeebacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {  
        List<Feedback> feedback = feedbackService.findAllFeedbacks(); 
        return ResponseEntity.ok(feedback); 
    }

    @DeleteMapping("/feedback/{id}")
    public void deleteUser(@PathVariable Long id) {
        feedbackService.deleteById(id);
    }

    @GetMapping("/feedbackCount")
    public ResponseEntity<Long> countFeedbacks() {
        long count = feedbackService.countFeedback();
        return ResponseEntity.ok(count);
    }

}
