package bt.edu.gcit.usermicroservice.rest;

import bt.edu.gcit.usermicroservice.entity.User;
import bt.edu.gcit.usermicroservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;
    private final Random random = new Random();

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User save(@RequestBody User user) {
        int otp = generateOTP();
        user.setOtp(otp);
        return userService.save(user);
    }

    private int generateOTP() {
        return 100000 + random.nextInt(900000);
    }
}
