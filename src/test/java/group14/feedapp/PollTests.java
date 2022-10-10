package group14.feedapp;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import group14.feedapp.web.PollCreateRequest;
import group14.feedapp.web.PollWeb;
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
        String userId = "10";
        final String baseUrl = "http://localhost:" + SERVER_PORT + "/api/poll";
        URI uri = new URI(baseUrl);
        User user = new User();
        user.setId("10");
        user.setName("Lars");
        user.setAdmin(false);

        PollCreateRequest poll = new PollCreateRequest();
        poll.setAnswerA("yes");
        poll.setAnswerB("No");
        poll.setQuestion("Do you like fish?");
        poll.setPincode(101010);
        poll.setPrivate(false);
        poll.setClosed(false);
        poll.setStartDate(LocalDateTime.of(2022, 10, 10,16, 0));
        poll.setEndDate(LocalDateTime.of(2022, 10, 10, 18, 0));

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", userId);

        HttpEntity<PollCreateRequest> request = new HttpEntity<>(poll, headers);

        ResponseEntity<PollWeb> result = this.restTemplate.postForEntity(uri, request, PollWeb.class);

        Assert.assertEquals(result.getBody().getClass(), PollWeb.class);

    }


}
