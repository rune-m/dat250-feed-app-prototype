package group14.feedapp;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.web.UserCreateRequest;
import group14.feedapp.web.UserWeb;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import java.net.URI;
import java.util.List;

@SpringBootTest
public class UserTests {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    final static String SERVER_PORT = "8080";

    @BeforeAll
    public static void startRESTServer() {
        FeedAppApplication.main(new String[]{SERVER_PORT});
    }


    @Test
    void adminCanGetAllUsers() throws URISyntaxException {
        String id = "40"; //I inserted an admin user into the database with id 40
        final String baseUrl = "http://localhost:" + SERVER_PORT + String.format("/api/user/all/%s", id);
        URI uri = new URI(baseUrl);

        ResponseEntity<UserWeb[]> result = this.restTemplate.getForEntity(uri, UserWeb[].class);
        Assert.assertNotNull(result.getBody());
    }

    @Test
    void notAdminCannotGetAllUsers() throws URISyntaxException {
        String id = "30"; //I inserted an admin user into the database with id 40
        final String baseUrl = "http://localhost:" + SERVER_PORT + String.format("/api/user/all/%s", id);
        URI uri = new URI(baseUrl);

        ResponseEntity<UserWeb[]> result = this.restTemplate.getForEntity(uri, UserWeb[].class);
        Assert.assertNull(result.getBody());
    }


}
