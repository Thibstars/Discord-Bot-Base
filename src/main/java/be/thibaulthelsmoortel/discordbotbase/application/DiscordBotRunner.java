package be.thibaulthelsmoortel.discordbotbase.application;

import be.thibaulthelsmoortel.discordbotbase.commands.core.Command;
import be.thibaulthelsmoortel.discordbotbase.commands.core.CommandType;
import be.thibaulthelsmoortel.discordbotbase.commands.core.ParameterStrategy;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
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
    private final List<Command> commands;

    @Autowired
    public DiscordBotRunner(DiscordBotEnvironment discordBotEnvironment, List<Command> commands) {
        this.discordBotEnvironment = discordBotEnvironment;
        this.commands = commands;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        handleMessage(event, msg);
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(discordBotEnvironment.getCommandPrefix())) {
            event.getChannel().sendTyping().queue();

            String parsedMessage = msg.substring(discordBotEnvironment.getCommandPrefix().length());

            if (StringUtils.isNotBlank(parsedMessage)) {
                AtomicBoolean commandRecognised = new AtomicBoolean(false);

                commands.forEach(command -> {
                    CommandType commandType = command.getClass().getAnnotation(CommandType.class);
                    String commandName = commandType.name();

                    if (parsedMessage.startsWith(commandName)) {
                        if (commandType.strategy() == ParameterStrategy.DISABLED) {
                            commandRecognised.set(true);
                            command.execute(event, null);
                        } else if (commandType.strategy() == ParameterStrategy.ENABLED) {
                            commandRecognised.set(true);
                            String parameters = parsedMessage.substring(parsedMessage.indexOf(commandType.name()) + commandType.name().length());
                            command.execute(event, Arrays.asList(parameters.split(" ")));
                        }
                    }
                });

                if (!commandRecognised.get()) {
                    event.getChannel().sendMessage("Command not recognized...").queue();
                }
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
                .awaitReady();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
