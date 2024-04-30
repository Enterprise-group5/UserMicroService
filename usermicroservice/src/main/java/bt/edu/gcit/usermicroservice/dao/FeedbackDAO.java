package bt.edu.gcit.usermicroservice.dao;

import java.util.List;

import bt.edu.gcit.usermicroservice.entity.Feedback;
import bt.edu.gcit.usermicroservice.entity.User;

public interface FeedbackDAO {

    Feedback save (Feedback feedback);

    List<Feedback> findAllFeedbacks();

    Feedback findByID(Long id);

    void deleteById(Long id);

    long countFeedback();


}
