package academy.devdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
public class AnimeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnimeServiceApplication.class, args);
	}
}
