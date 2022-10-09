package group14.feedapp.service;

import group14.feedapp.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IUserService {

    User getUserById(String id);
    User createUser(@RequestBody User user);

    User updateUser(User authenticatedUser, User updatedUser);
    List<User> getAllUsers(User user);

}
