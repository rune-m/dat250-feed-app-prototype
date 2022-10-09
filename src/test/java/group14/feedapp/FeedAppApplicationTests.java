package group14.feedapp;

import group14.feedapp.model.Poll;
import group14.feedapp.model.User;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.google.gson.Gson;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


@SpringBootTest
class FeedAppApplicationTests {

	private static final String SERVER_PORT = "8080";
	private static final String BASE_URL = "http://localhost:" + SERVER_PORT + "/";

	private final OkHttpClient client = new OkHttpClient();
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private final Gson gson = new Gson();

	@BeforeClass
	public static void startRESTServer() {
		FeedAppApplication.main(new String[]{SERVER_PORT});
	}

	@Test
	void contextLoads() {
	}

	@Test
	void canCreate(){
		User user = new User();
		user.setAdmin(false);
		user.setName("Lars");
		user.setId("10");

		Poll poll = new Poll(1234,
							"Do you like fish?",
							"Yes",
							"No",
							false,
							LocalDateTime.of(2022, 10, 7, 13, 50),
							LocalDateTime.of(2022, 10, 8, 13, 50),
							false,
							user
							);

		String response = doPostRequest(poll);

		System.out.println(response);




	}

	private String doPostRequest(Poll poll){
		RequestBody body = RequestBody.create(gson.toJson(poll), JSON);

		Request request =new Request.Builder()
				.url(BASE_URL)
				.post(body)
				.build();

		return doRequest(request);
	}

	private String doRequest(Request request) {
		try (Response response = client.newCall(request).execute()) {
			return Objects.requireNonNull(response.body()).string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
