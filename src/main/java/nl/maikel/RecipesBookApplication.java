package nl.maikel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class RecipesBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesBookApplication.class, args);
	}
}
