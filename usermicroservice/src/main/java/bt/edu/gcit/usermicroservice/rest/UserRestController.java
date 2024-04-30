package bt.edu.gcit.usermicroservice.rest;

import bt.edu.gcit.usermicroservice.entity.User;
import bt.edu.gcit.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerCustomer")
    public User registerCustomer(@RequestBody User customer) {
        return userService.registerCustomer(customer);
    }

    @PostMapping("/registerAdmin")
    public User registerAdmin(@RequestBody User admin) {
        return userService.registerAdmin(admin);
    }

    @PostMapping("/registerOwner")
    public User registerOwner(@RequestBody User owner) {
        return userService.registerOwner(owner);
    }

    // @GetMapping("/users/checkDuplicateEmail")
    // public ResponseEntity<Boolean> checkDuplicateEmail(@RequestParam String
    // email) {
    // boolean isDuplicate = userService.isEmailDuplicate(email);
    // return ResponseEntity.ok(isDuplicate);
    // }

    // @GetMapping("/customer/checkemailunique")
    // public ResponseEntity<Boolean> isEmailUnique(@RequestParam("email") String email) {
    //     boolean isUnique = userService.isCustomerEmailUnique(email);
    //     return ResponseEntity.ok(isUnique);
    // }

    /**
     * Updates a user with the given ID using the provided User object.
     *
     * @param id          the ID of the user to be updated
     * @param updatedUser the User object containing the updated information
     *                    }
     * @return the updated User object
     */

    // @PutMapping("/users/{email}")
    // public User updateUser(@PathVariable String email, @RequestBody User
    // updatedUser) {
    // return userService.updateUser(email, updatedUser);
    // }

    @DeleteMapping("/deleteCustomer/{email}")
    public void deleteCustomer(@PathVariable String email) {
        userService.deleteCustomerByEmail(email);
    }
    @DeleteMapping("/deleteOwner/{email}")
    public void deleteOwner(@PathVariable String email) {
        userService.deleteOwnerByEmail(email);
    }

    @GetMapping("/customer/{email}")
    public void getCustomerByEmail(@PathVariable String email) {
        userService.getCustomerByEmail(email);
    }

    // /**
    // * Update the enabled status of a user with the specified id
    // *
    // * @param id The ID of the user to update
    // * @param enabled The new enabled status
    // * @return OK if the update was successful
    // */
    // @PutMapping("/users/{id}/enabled")
    // public ResponseEntity<?> updateUserEnabledStatus(@PathVariable int id,
    // @RequestBody Map<String, Boolean> requestBody) {
    // Boolean enabled = requestBody.get("enabled");
    // userService.updateUserEnabledStatus(id, enabled);
    // System.out.println("User enabled status updated successfully");
    // return ResponseEntity.ok().build();
    // }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll(); 
        return ResponseEntity.ok(users);
    }

    @GetMapping("/allCustomer")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> users = userService.getAllCustomers(); 
        return ResponseEntity.ok(users); 
    }

    @GetMapping("/allOwner")
    public ResponseEntity<List<User>> getAllOwners() {
        List<User> owners = userService.getAllOwners(); 
        return ResponseEntity.ok(owners); 
    }

    @GetMapping("/countCustomers")
    public ResponseEntity<Long> countCustomers() {
        long customerCount = userService.countCustomers(); 
        return ResponseEntity.ok(customerCount); 
    }

    @GetMapping("/countOwners")
    public ResponseEntity<Long> countOwners() {
        long ownerCount = userService.countOwners(); 
        return ResponseEntity.ok(ownerCount); 
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<?> verifyOtpCustomer(@RequestBody User request) {
        String email = request.getEmail();
        String enteredotp = request.getOtp();

        boolean isVerified = userService.verifyOtpCustomer(email, enteredotp);

        if (isVerified) {
            return ResponseEntity.ok("User verified successfully.");
        } else {
            return ResponseEntity.status(400).body("Invalid OTP.");
        }
    }
}
