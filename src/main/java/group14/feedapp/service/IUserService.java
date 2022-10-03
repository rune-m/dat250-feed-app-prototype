package group14.feedapp.service;

import group14.feedapp.model.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserService {

    User getUserById(String id);
    User createUser(@RequestBody User user);

}
