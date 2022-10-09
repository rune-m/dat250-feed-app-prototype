package group14.feedapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@SpringBootTest
class FeedAppApplicationTests {



	private TestRestTemplate restTemplate = new TestRestTemplate();

	@LocalServerPort
	final static String SERVER_PORT = "8080";

	@BeforeAll
	public static void startRESTServer() {
		FeedAppApplication.main(new String[]{SERVER_PORT});
	}

	@Test
	void contextLoads() {
	}



}
