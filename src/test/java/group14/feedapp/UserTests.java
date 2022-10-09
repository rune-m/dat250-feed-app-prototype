package group14.feedapp;

import group14.feedapp.exception.NoAccessException;
import group14.feedapp.exception.ResourceNotFoundException;
import group14.feedapp.model.User;
import group14.feedapp.repository.PollRepository;
import group14.feedapp.repository.VoteRepository;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IPollService;
import group14.feedapp.service.IUserService;
import group14.feedapp.service.IVoteService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
@SpringBootTest
public class UserTests {

    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserService userService;
    private WebMapper mapper = new WebMapper();
    @Test
    void updateUser_isAdmin_updatesUser() {
        var authorizedUser = authService.getAuthorizedUser("40"); // admin
        var userToBeUpdated = userService.getUserById("10");

        userToBeUpdated.setAdmin(true);

        userService.updateUser(authorizedUser, userToBeUpdated);

        var updatedUser = userService.getUserById("10");

        assertEquals(updatedUser.isAdmin(), true);
    }
    @Test
    void updateUser_isNotAdmin_throwsException() {
        var authorizedUser = authService.getAuthorizedUser("30"); // not admin
        var userToBeUpdated = userService.getUserById("10");

        userToBeUpdated.setAdmin(true);

        assertThrowsExactly(
                NoAccessException.class,
                () -> userService.updateUser(authorizedUser, userToBeUpdated)
                );
    }
    @Test
    void createUser_isCorrect_createsUser() {
        var userRequest = new UserCreateRequest();
        userRequest.setName("test");
        var userToBeCreated = mapper.getMapper().map(userRequest, User.class);

        var newUser = userService.createUser(userToBeCreated);

        assertEquals(newUser.getName(), "test");
        assertEquals(newUser.isAdmin(), false);
        assertFalse(newUser.getId() == null);
    }
}