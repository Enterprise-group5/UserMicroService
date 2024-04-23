package bt.edu.gcit.usermicroservice.dao;

import bt.edu.gcit.usermicroservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        System.out.println(users.size());
        if (users.isEmpty()) {
            return null;
        } else {
            System.out.println(users.get(0) + " " + users.get(0).getEmail());
            return users.get(0);
        }
    }

    @Override
    public User findByID(int theId) {
        // Implement the logic to find a user by their ID in the database
        // and return the user object
        User user = entityManager.find(User.class, theId);
        return user;
    }

}
