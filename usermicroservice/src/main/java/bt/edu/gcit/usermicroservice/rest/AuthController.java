package bt.edu.gcit.usermicroservice.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bt.edu.gcit.usermicroservice.entity.User;
import bt.edu.gcit.usermicroservice.service.AuthService;
import bt.edu.gcit.usermicroservice.service.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user, HttpServletResponse response) {
        UserDetails userDetails = authService.login(user.getEmail(), user.getPassword());
        String jwt = jwtUtil.generateToken(userDetails);
        // Set JWT as a cookie
        Cookie jwtCookie = new Cookie("JWT-TOKEN", jwt);
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", userDetails);

        return ResponseEntity.ok(responseBody);

        // Map<String, Object> response = new HashMap<>();
        // response.put("jwt", jwt);
        // response.put("user", userDetails);

        // return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Clear JWT cookie
        Cookie jwtCookie = new Cookie("JWT-TOKEN", null);
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok().build();
    }

}
