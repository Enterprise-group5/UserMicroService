package bt.edu.gcit.usermicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;

import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "feedback")
public class Feedback {
    public Feedback() {
        // Default constructor
    }

    public Feedback(String userName, String email, String feedbackMessage) {
        this.userName = userName;
        this.email = email;
        this.feedbackMessage = feedbackMessage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "users_name", nullable = false, length = 40)
    private String userName;
    @Column(name = "email", nullable = false, length = 150)
    private String email;
    @Column(name = "feedback_message", nullable = false, length = 150)
    private String feedbackMessage;

    // Getters
    public int getId() {
        return id;
    }

    public String getuserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getfeedbackMessage() {
        return feedbackMessage;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public void setfeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}