package bt.edu.gcit.usermicroservice.service;

import java.util.List;

import bt.edu.gcit.usermicroservice.entity.Feedback;
import bt.edu.gcit.usermicroservice.entity.User;

public interface FeedbackService {

    Feedback save (Feedback feedback);

    List<Feedback> findAllFeedbacks();

    void deleteById(Long id);

    long countFeedback();


}
