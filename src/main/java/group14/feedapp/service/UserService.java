package group14.feedapp.service;

import group14.feedapp.model.User;
import group14.feedapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getUserById(String id) {
        Optional<User> accountOptional = repository.findById(id);
        return accountOptional.isPresent() ? accountOptional.get() : null;
    }

    @Override
    public User createUser(User user) {
        User newUser = repository.save(user);
        return newUser;
    }

    @Override
    public List<User> getAllUsers(User user) {
        if (!user.isAdmin()){
            return null;
        }
        return repository.findAll();
    }


}
