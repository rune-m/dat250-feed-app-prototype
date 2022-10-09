package group14.feedapp;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
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
import java.time.LocalDateTime;

@SpringBootTest
public class PollTests {
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    final static String SERVER_PORT = "8080";

    @BeforeAll
    public static void startRESTServer() {
        FeedAppApplication.main(new String[]{SERVER_PORT});
    }


    @Test
    void createPoll() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + SERVER_PORT + "/api/poll/";
        URI uri = new URI(baseUrl);
        User user = new User();
        user.setId("10");
        user.setName("Lars");
        user.setAdmin(false);

        Poll poll = new Poll(10101,
                "Do you like fish?",
                "Yes",
                "No",
                false,
                LocalDateTime.of(2022, 10, 10,16, 0),
                LocalDateTime.of(2022, 10, 10, 18, 0),
                false,
                user);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Poll> request = new HttpEntity<>(poll, headers);

        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals("SUCCESS", result.getBody());

    }


}
