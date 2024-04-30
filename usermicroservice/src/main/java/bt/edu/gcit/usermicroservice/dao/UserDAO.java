package bt.edu.gcit.usermicroservice.dao;

import java.util.List;
import java.util.Optional;
import bt.edu.gcit.usermicroservice.entity.User;

public interface UserDAO {

    boolean isEmailExists(String email);

    User registerCustomer(User customer);

    User registerAdmin(User admin);

    User registerOwner(User owner);

    User findByEmail(String email);

    void deleteCustomerByEmail(String email);

    void deleteOwnerByEmail(String email);

    List<User> getAllCustomers();

    List<User> getAllOwners();

    User getCustomerByEmail(String email);

    long countCustomers();

    long countOwners();

    // void updateUserEnabledStatus(int email, boolean enabled);

    List<User> findAll();

    boolean verifyOtpCustomer(String email, String enteredOtp);

}