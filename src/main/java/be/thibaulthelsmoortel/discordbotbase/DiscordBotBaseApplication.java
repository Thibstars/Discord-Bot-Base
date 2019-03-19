package be.thibaulthelsmoortel.discordbotbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Spring boot application entry point.
 *
 * @author Thibault Helsmoortel
 */
@EnableConfigurationProperties
@SpringBootApplication
public class DiscordBotBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordBotBaseApplication.class, args);
	}

}
