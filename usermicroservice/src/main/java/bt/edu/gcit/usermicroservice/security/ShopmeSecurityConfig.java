package bt.edu.gcit.usermicroservice.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ShopmeSecurityConfig {
    public ShopmeSecurityConfig() {
        System.out.println("ShopmeSecurityConfig created");
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        System.out.println("H2i ");
        return new ProviderManager(Arrays.asList(authProvider()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        System.out.println("Hi ");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        System.out.println("UserDetailsService: " + userDetailsService.getClass().getName());
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        System.out.println("AuthProvider: " + authProvider.getClass().getName());

        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/roles").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/allroles").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.POST, "/api/auth/logout").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/registerCustomer").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/registerAdmin").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/registerOwner").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/allUsers").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/allCustomer").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/allOwner").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/users/checkDuplicateEmail").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/countCustomers").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET, "/api/countOwners").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.DELETE, "/api/deleteCustomer/{email}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/deleteOwner/{email}").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/allfeebacks").hasAuthority("Admin")
                .requestMatchers(HttpMethod.POST,"/api/verifyotp").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/customer/{email}").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/customer/all").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/feedback").hasAnyAuthority("Customer")
                .requestMatchers(HttpMethod.DELETE,"/api/feedback/{id}").hasAnyAuthority("Admin")
                .requestMatchers(HttpMethod.GET,"/api/feedbackCount").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/customerLogin").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/auth/customerLogin").hasAnyAuthority("Customer")




        )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // disable CSRF
        http.csrf().disable();

        return http.build();
    }

}
