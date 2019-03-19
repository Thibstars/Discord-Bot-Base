package be.thibaulthelsmoortel.discordbotbase.application;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component running the discord bot.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class DiscordBotRunner extends ListenerAdapter implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordBotRunner.class);

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public DiscordBotRunner(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        handleMessage(event, msg);
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(discordBotEnvironment.getCommandPrefix())) {
            String parsedMessage = msg.substring(discordBotEnvironment.getCommandPrefix().length());
            AtomicBoolean commandRecognised = new AtomicBoolean(false);

            if (commandRecognised.get()) {

            } else {
                event.getChannel().sendMessage("Command not recognized...").queue();
            }
        }
    }

    @Override
    public void run(String... args) {
        try {
            new JDABuilder(AccountType.BOT)
                .setToken(discordBotEnvironment.getToken())
                .addEventListener(this)
                .build()
                .awaitReady();// There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        } catch (LoginException | InterruptedException e) {
            // If anything goes wrong in terms of authentication, this is the exception that will represent it
            LOGGER.error(e.getMessage(), e);
        }
    }
}
