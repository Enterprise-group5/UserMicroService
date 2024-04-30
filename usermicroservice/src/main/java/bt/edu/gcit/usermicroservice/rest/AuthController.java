package bt.edu.gcit.usermicroservice.rest;

import bt.edu.gcit.usermicroservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import bt.edu.gcit.usermicroservice.service.AuthService;
import bt.edu.gcit.usermicroservice.service.JWTUtil;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
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

    @PostMapping("/customerLogin")
    public ResponseEntity<Map<String, Object>> customerLogin(@RequestBody User user) {
        try {
            // Step 1: Authenticate the User
            UserDetails userDetails = authService.login(user.getEmail(), user.getPassword());

            // Step 2: Check if User has the "Customer" Role
            boolean isCustomer = userDetails.getAuthorities().stream()
                    .anyMatch(authority -> "Customer".equalsIgnoreCase(authority.getAuthority()));

            if (!isCustomer) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("error", "User is not a customer"));
            }

            // Step 3: Check if User is Enabled
            boolean isEnabled = user.isEnabled(); // Assuming 'isEnabled' indicates the enabled status

            if (!isEnabled) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error",
                                "Your account is not yet verified, please verify first"));
            }

            // Step 4: Generate JWT Token
            String jwt = jwtUtil.generateToken(userDetails);

            // Step 5: Prepare Response with JWT and User Details
            Map<String, Object> response = new HashMap<>();
            response.put("jwt", jwt);
            response.put("user", userDetails);

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials"));
        }

    }
}
