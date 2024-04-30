package bt.edu.gcit.usermicroservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bt.edu.gcit.usermicroservice.dao.FeedbackDAO;
import bt.edu.gcit.usermicroservice.entity.Feedback;
import bt.edu.gcit.usermicroservice.entity.User;
import jakarta.transaction.Transactional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDAO feedbackDAO;

    @Autowired
    public FeedbackServiceImpl(FeedbackDAO feedbackDAO) {
        this.feedbackDAO = feedbackDAO;
    }

    @Override
    @Transactional
    public Feedback save(Feedback feedback) {
        return feedbackDAO.save(feedback);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return feedbackDAO.findAllFeedbacks(); 
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        feedbackDAO.deleteById(id);
    }

    @Override
    @Transactional
    public long countFeedback() {
        return feedbackDAO.countFeedback();
    }
}
