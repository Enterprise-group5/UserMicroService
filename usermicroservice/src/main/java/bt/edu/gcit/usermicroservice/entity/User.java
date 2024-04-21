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

    @Column(name = "UserName", length = 45, nullable = false)
    private String UserName;

    @Column(name = "MobileNo", unique = true)
    private Integer MobileNo;

    @Column(length = 64, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public User() {
        // Empty constructor
    }

    public User(String email,Integer MobileNo, String password, String UserName) {
        this.email = email;
        this.MobileNo = MobileNo;
        this.password = password;
        this.UserName = UserName;
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

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String UserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
