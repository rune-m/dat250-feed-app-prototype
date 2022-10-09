package group14.feedapp.service;

import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    @Autowired
    IUserService userService;

    @Override
    public User getAuthorizedUser(String token) {
        var userId = getUserIdFromToken(token);
        var user = userService.getUserById(userId);
        if (user == null){
            throw new ResourceNotFoundException(userId);
        }
        return user;
    }

    private String getUserIdFromToken(String token){
        // Prototype solution, should be switched out with a real solution
        return token;
    }
}
