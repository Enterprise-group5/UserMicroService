package bt.edu.gcit.usermicroservice.service;

import bt.edu.gcit.usermicroservice.entity.User;

public interface UserService {
    User save(User user);

    boolean isEmailDuplicate(String email);

    User updateUser(int id, User updatedUser);
}