package be.thibaulthelsmoortel.discordbotbase;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${bot.token}")
    private String token;

    @Value("${bot.command.prefix}")
    private String prefix;

    @Value("${bot.command.alternate.prefix}")
    private String alternatePrefix;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        handleMessage(event, msg);
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(prefix)) {
            String parsedMessage = msg.substring(prefix.length());
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
                .setToken(token)
                .addEventListener(this)
                .build()
                .awaitReady();// There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        } catch (LoginException | InterruptedException e) {
            // If anything goes wrong in terms of authentication, this is the exception that will represent it
            LOGGER.error(e.getMessage(), e);
        }
    }
}
