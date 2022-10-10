package group14.feedapp;


import group14.feedapp.exception.NoAccessException;
import group14.feedapp.model.User;
import group14.feedapp.service.IAuthService;
import group14.feedapp.service.IUserService;
import group14.feedapp.utils.WebMapper;
import group14.feedapp.web.UserCreateRequest;
import group14.feedapp.web.UserWeb;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
@SpringBootTest
public class UserTests {

    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserService userService;
    private WebMapper mapper = new WebMapper();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    final static String SERVER_PORT = "8080";

    @BeforeAll
    public static void startRESTServer() {
        FeedAppApplication.main(new String[]{SERVER_PORT});
    }
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
        assertNotNull(newUser.getId());
    }

/*    @Test
    void adminCanGetAllUsers() throws URISyntaxException {
        String id = "40"; //I inserted an admin user into the database with id 40
        final String baseUrl = "http://localhost:" + SERVER_PORT + String.format("/api/user/all/%s", id);
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", id);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<UserWeb[]> result = this.restTemplate.exchange(uri, HttpMethod.GET, requestEntity, UserWeb[].class);

        Assert.assertNotNull(result.getBody());
    }*/

    @Test
    void notAdminCannotGetAllUsers() throws URISyntaxException {
        String id = "30"; //I inserted an admin user into the database with id 40
        final String baseUrl = "http://localhost:" + SERVER_PORT + String.format("/api/user/all/%s", id);
        URI uri = new URI(baseUrl);

        ResponseEntity<UserWeb[]> result = this.restTemplate.getForEntity(uri, UserWeb[].class);
        Assert.assertNull(result.getBody());
    }
}
