package be.thibaulthelsmoortel.discordbotbase.config;

import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.provider.discord.DiscordProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Thibault Helsmoortel
 */
@Configuration
public class DiscordConfig {

    @Bean
    public DiscordProvider discordProvider() {
        return new DiscordProvider(discordTokenAuthentication());
    }

    @Bean
    public DiscordTokenAuthentication discordTokenAuthentication() {
        return new DiscordTokenAuthentication(new DiscordTokenAuthenticationHandler());
    }

}
