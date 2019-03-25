package be.thibaulthelsmoortel.discordbotbase.application;

import be.thibaulthelsmoortel.discordbotbase.commands.core.CommandExecutor;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.Objects;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildReadyEvent;
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
    private final CommandExecutor commandExecutor;

    @Autowired
    public DiscordBotRunner(DiscordBotEnvironment discordBotEnvironment,
        CommandExecutor commandExecutor) {
        this.discordBotEnvironment = discordBotEnvironment;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        handleMessage(event, msg);
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        TextChannel textChannel = Objects.requireNonNull(event.getGuild().getDefaultChannel());
        textChannel.sendTyping().queue();
        textChannel.sendMessage(discordBotEnvironment.getName() + " reporting for duty!").queue();
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(discordBotEnvironment.getCommandPrefix())) {
            event.getChannel().sendTyping().queue();

            String parsedMessage = msg.substring(discordBotEnvironment.getCommandPrefix().length());

            commandExecutor.tryExecute(event, parsedMessage);
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
