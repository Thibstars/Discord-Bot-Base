package be.thibaulthelsmoortel.discordbotbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Discord bot environment.
 *
 * @author Thibault Helsmoortel
 */
@Component
@PropertySource("classpath:token.properties")
@ConfigurationProperties(prefix = "bot")
public class DiscordBotEnvironment {

    private String token;

    private String commandPrefix;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }
}
