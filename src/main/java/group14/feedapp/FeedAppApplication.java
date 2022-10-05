package group14.feedapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("group14.feedapp.repository")
@OpenAPIDefinition(info = @Info(title = "FeedApp API"))
public class FeedAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeedAppApplication.class, args);
	}

}
