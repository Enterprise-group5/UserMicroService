package bt.edu.gcit.usermicroservice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bt.edu.gcit.usermicroservice.entity.Feedback;
import bt.edu.gcit.usermicroservice.entity.User;
import jakarta.persistence.EntityManager;

@Repository
public class FeedbackDAOImpl implements FeedbackDAO {
    private EntityManager entityManager;

    @Autowired
    public FeedbackDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Feedback save(Feedback feedback) {
        return entityManager.merge(feedback);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        return entityManager.createQuery("SELECT u FROM Feedback u", Feedback.class).getResultList();
    }

    @Override
    public Feedback findByID(Long id) {
        Feedback feedback = entityManager.find(Feedback.class, id);
        return feedback;
    }

    @Override
    public void deleteById(Long id) {
        Feedback feedback = findByID(id);
        entityManager.remove(feedback);
    }

    @Override
    public long countFeedback() {
        return entityManager.createQuery("SELECT COUNT(f) FROM Feedback f", Long.class).getSingleResult();
    }
}