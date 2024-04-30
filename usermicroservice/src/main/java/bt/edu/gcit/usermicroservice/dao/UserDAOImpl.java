package bt.edu.gcit.usermicroservice.dao;

import bt.edu.gcit.usermicroservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import bt.edu.gcit.usermicroservice.exception.UserNotFoundException;

@Repository
public class UserDAOImpl implements UserDAO {
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isEmailExists(String email) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email",
                Long.class);
        query.setParameter("email", email);
        long count = query.getSingleResult();
        return count > 0; // Returns true if the email exists
    }

    @Override
    public User registerCustomer(User customer) {
        User existingUser = findByEmail(customer.getEmail());

        if (existingUser != null) {
            boolean hasCustomerRole = customer.getRoles()
                    .stream()
                    .anyMatch(role -> "Customer".equalsIgnoreCase(role.getName()));
            if (hasCustomerRole) {
                throw new IllegalArgumentException("Email is already registered as a customer");
            } else {
                throw new IllegalArgumentException("Email is already registered as another user");
            }
        }
        return entityManager.merge(customer);
    }

    @Override
    public User registerAdmin(User admin) {
        // Check if the email already exists
        if (isEmailExists(admin.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        return entityManager.merge(admin);
    }

    @Override
    public User registerOwner(User owner) {
        if (isEmailExists(owner.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        return entityManager.merge(owner);
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
    public User getCustomerByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No user found with email: " + email);
            return null;
        }
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        User user = findByEmail(email); // This method finds the user by email

        if (user == null) {
            System.out.println("User not found");
            return; // Exit early if the user doesn't exist
        }

        boolean hasCustomerRole = user.getRoles() // Get the roles of the user
                .stream() // Stream the roles
                .anyMatch(role -> "Customer".equalsIgnoreCase(role.getName())); // Check if any role has the name
                                                                                // "Customer"

        if (hasCustomerRole) { // If the user has a customer role, delete them
            entityManager.remove(user);
        } else {
            System.out.println("User does not have the 'Customer' role, not deleting.");
        }
    }

    @Override
    public void deleteOwnerByEmail(String email) {
        User user = findByEmail(email); // This method finds the user by email

        if (user == null) {
            System.out.println("User not found");
            return; // Exit early if the user doesn't exist
        }

        boolean hasCustomerRole = user.getRoles() // Get the roles of the user
                .stream() // Stream the roles
                .anyMatch(role -> "Owner".equalsIgnoreCase(role.getName())); // Check if any role has the name
                                                                                // "Customer"

        if (hasCustomerRole) { // If the user has a customer role, delete them
            entityManager.remove(user);
        } else {
            System.out.println("User does not have the 'Customer' role, not deleting.");
        }
    }

    @Transactional
    public List<User> getAllCustomers() {
        // Retrieve all users from the database
        List<User> allUsers = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();

        // Filter users to only include those with the "Customer" role
        List<User> customers = allUsers.stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> "Customer".equalsIgnoreCase(role.getName())))
                .collect(Collectors.toList());

        return customers;
    }

    @Transactional
    public List<User> getAllOwners() {
        // Retrieve all users from the database
        List<User> allUsers = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();

        // Filter users to only include those with the "Customer" role
        List<User> owner = allUsers.stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> "Owner".equalsIgnoreCase(role.getName())))
                .collect(Collectors.toList());

        return owner;
    }

    @Override
    public long countCustomers() {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'Customer'", Long.class)
                .getSingleResult();

        return count;
    }

    @Override
    public long countOwners() {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'Owner'", Long.class)
                .getSingleResult();

        return count;
    }

    // @Override
    // public void updateUserEnabledStatus(int id, boolean enabled) {
    // User user = entityManager.find(User.class, id);
    // System.out.println(user);
    // if (user == null) {
    // throw new UserNotFoundException("User not found with id " + id);
    // }
    // user.setEnabled(enabled);
    // entityManager.persist(user);
    // }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public boolean verifyOtpCustomer(String email, String enteredOtp) {
        User customer = entityManager.find(User.class, email);
        // System.out.println(otp);
        // String otp1 = user.getOtp();
        // System.out.println(otp1);
        if (customer != null && customer.getOtp().equals(enteredOtp)) {
            customer.setEnabled(true);
            return true;
        }
        // System.out.println(enteredOtp);
        return false;
    }

}