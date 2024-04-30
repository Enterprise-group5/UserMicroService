package bt.edu.gcit.usermicroservice.service;

import java.util.List;
import java.util.Optional;

import bt.edu.gcit.usermicroservice.entity.User;

public interface UserService {

    boolean isEmailExists(String email);

    User registerCustomer(User customer);

    User registerAdmin(User admin);

    User registerOwner(User owner);

    // User updateUser(String email, User updatedUser);

    void deleteCustomerByEmail(String email);

    void deleteOwnerByEmail(String email);

    List<User> getAllCustomers();

    List<User> getAllOwners();

    User getCustomerByEmail(String email);

    long countCustomers();

    long countOwners();

    // void updateUserEnabledStatus(int id, boolean enabled);

    List<User> findAll();

    boolean verifyOtpCustomer(String email, String enteredOtp);

}