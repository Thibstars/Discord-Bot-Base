package be.thibaulthelsmoortel.discordbotbase.application;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Startup application listener performing tasks upon startup.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupApplicationListener.class);

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public StartupApplicationListener(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        LOGGER.info("Application started.");
        LOGGER.info("Author: {}", discordBotEnvironment.getAuthor());
    }
}
