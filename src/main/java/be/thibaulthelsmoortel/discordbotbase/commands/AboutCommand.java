package be.thibaulthelsmoortel.discordbotbase.commands;

import be.thibaulthelsmoortel.discordbotbase.commands.core.BotCommand;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Basic command providing general information on the bot.
 *
 * @author Thibault Helsmoortel
 */
@Command(name = "about", description = "Provides general information about the bot.")
@Component
public class AboutCommand extends BotCommand {

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public AboutCommand(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public Object call() {
        String message = null;

        if (event instanceof GenericMessageEvent) {
            if (StringUtils.isAllBlank(discordBotEnvironment.getName(), discordBotEnvironment.getAuthor())) {
                message = "Mystery bot by mystery author.";
            } else {
                message = (StringUtils.isNotBlank(discordBotEnvironment.getName()) ? discordBotEnvironment.getName() : "Bot ")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getAuthor()) ? " created by " + discordBotEnvironment.getAuthor() + "." : "")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getVersion()) ? " Version: " + discordBotEnvironment.getVersion() : "");
            }
            ((GenericMessageEvent) event).getChannel().sendMessage(message).queue();
        }

        return message;
    }
}
