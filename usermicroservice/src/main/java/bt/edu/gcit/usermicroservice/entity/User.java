package bt.edu.gcit.usermicroservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import java.util.HashSet;
import java.util.Set;
import bt.edu.gcit.usermicroservice.entity.Role;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;
    @Column(name = "contact_number", length = 45, nullable = false)
    private String contactNumber;
    @Column(length = 64, nullable = false)
    private String password;
    @Column(length = 6)
    private String otp;
    @Column(nullable = false)
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>();

    // Constructors
    public User() {
        // Empty constructor
    }

    public User(String email, String password, String userName, String contactNumber) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.contactNumber = contactNumber;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getcontactNumber() {
        return contactNumber;
    }

    public void setcontactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
