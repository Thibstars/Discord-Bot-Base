package be.thibaulthelsmoortel.discordbotbase.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Properties configuration.
 *
 * @author Thibault Helsmoortel
 */
@Configuration
@EnableConfigurationProperties({DiscordBotEnvironment.class})
@PropertySource("classpath:token.properties")
public class PropertiesConfig {
}
