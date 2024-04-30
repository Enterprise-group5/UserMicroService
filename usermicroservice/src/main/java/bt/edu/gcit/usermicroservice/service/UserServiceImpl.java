package bt.edu.gcit.usermicroservice.service;

import bt.edu.gcit.usermicroservice.dao.UserDAO;
import bt.edu.gcit.usermicroservice.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.*;

import java.util.List;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import bt.edu.gcit.usermicroservice.exception.UserNotFoundException;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Random random;

    @Autowired
    @Lazy
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.random = new Random();
    }

    @Override
    @Transactional
    public User registerCustomer(User customer) {
        // Generate OTP
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        String otp = generateOTP();

        // Set OTP for the user
        customer.setOtp(otp);

        // Send OTP via email
        sendOTPEmail(customer.getEmail(), otp);

        // Save user with OTP
        return userDAO.registerCustomer(customer);
    }

    // Generate a 6-digit OTP
    private String generateOTP() {
        int otpNumber = 100000 + random.nextInt(900000); // Generates a random 6-digit number
        return String.valueOf(otpNumber);
    }

    // Method to send OTP via email
    private void sendOTPEmail(String recipientEmail, String otp) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jinpacr13@gmail.com", "fxba lrdl jxip qmaf");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("jinpacr13@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Your OTP for Verification");
            message.setText("Your OTP is: " + otp);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            System.err.println("Error sending email: " + mex.getMessage());
            mex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public User registerAdmin(User admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return userDAO.registerAdmin(admin);
    }

    @Override
    @Transactional
    public User registerOwner(User owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        return userDAO.registerOwner(owner);
    }

    @Override
    @Transactional
    public List<User> getAllCustomers() {
        return userDAO.getAllCustomers();
    }

    @Override
    @Transactional
    public List<User> getAllOwners() {
        return userDAO.getAllOwners();
    }

    @Override
    @Transactional
    public long countCustomers() {
        return userDAO.countCustomers();
    }

    @Override
    @Transactional
    public long countOwners() {
        return userDAO.countOwners();
    }

    @Transactional(readOnly = true)
    @Override
    public User getCustomerByEmail(String email) {
        return userDAO.getCustomerByEmail(email);
    }

    // @Override
    // public boolean isEmailDuplicate(String email) {
    // User user = userDAO.findByEmail(email);
    // return user != null;
    // }

    // @Transactional
    // @Override
    // public User updateUser(String email, User updatedUser) {
    //     // First, find the user by ID
    //     User existingUser = userDAO.findByEmail(email);
        
    //      List<User> customers = allUsers.stream()
    //             .filter(user -> user.getRoles().stream()
    //                     .anyMatch(role -> "Customer".equalsIgnoreCase(role.getName())))
    //             .collect(Collectors.toList());
    //     if (existingUser == null) {
    //         throw new UserNotFoundException("User not found with email: " + email);
    //     }
    //     // Update the existing user with the data from updatedUser
    //     existingUser.setUserName(updatedUser.getUserName());
    //     existingUser.setEmail(updatedUser.getEmail());
    //     // Check if the password has changed. If it has, encode the new password before
    //     // saving.
    //     if (!existingUser.getPassword().equals(updatedUser.getPassword())) {
    //         existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
    //     }
    //     existingUser.setRoles(updatedUser.getRoles());
    //     // Save the updated user and return it
    //     return userDAO.registerOwner(existingUser);
    // }

    @Transactional
    @Override
    public void deleteCustomerByEmail(String email) {
        userDAO.deleteCustomerByEmail(email);
    }

    
    @Transactional
    @Override
    public void deleteOwnerByEmail(String email) {
        userDAO.deleteOwnerByEmail(email);
    }


    // @Transactional
    // @Override
    // public void updateUserEnabledStatus(int id, boolean enabled) {
    // userDAO.updateUserEnabledStatus(id, enabled);
    // }

    @Override
    public List<User> findAll() {
        return userDAO.findAll(); // Get all users from the DAO
    }

    @Override
    @Transactional
    public boolean verifyOtpCustomer(String email, String enteredOtp) {
        // Call DAO method to verify OTP and enable user
        boolean otpVerified = userDAO.verifyOtpCustomer(email, enteredOtp);

        return otpVerified; // Return the result of the verification
    }

    @Override
    public boolean isEmailExists(String email) {
        return userDAO.isEmailExists(email);
    }

}