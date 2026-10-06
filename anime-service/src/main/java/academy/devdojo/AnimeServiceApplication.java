package academy.devdojo;

import academy.devdojo.config.ConnectionConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties(ConnectionConfigurationProperties.class)
public class AnimeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimeServiceApplication.class, args);
    }
}
