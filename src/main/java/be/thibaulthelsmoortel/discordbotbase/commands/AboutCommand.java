package be.thibaulthelsmoortel.discordbotbase.commands;

import be.thibaulthelsmoortel.discordbotbase.commands.core.Command;
import be.thibaulthelsmoortel.discordbotbase.commands.core.CommandType;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.Collection;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Basic command providing general information on the bot.
 *
 * @author Thibault Helsmoortel
 */
@CommandType(name = "about")
@Component
public class AboutCommand implements Command {

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public AboutCommand(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public void execute(Event event, Collection<String> parameters) {
        if (event instanceof GenericMessageEvent) {
            String message;
            if (StringUtils.isAllBlank(discordBotEnvironment.getName(), discordBotEnvironment.getAuthor())) {
                message = "Mistery bot by mistery author.";
            } else {
                message = (StringUtils.isNotBlank(discordBotEnvironment.getName()) ? discordBotEnvironment.getName() : "Bot ")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getAuthor()) ? " created by " + discordBotEnvironment.getAuthor() + "." : "")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getVersion()) ? " Version: " + discordBotEnvironment.getVersion() : "")
                    + ".";
            }
            ((GenericMessageEvent) event).getChannel().sendMessage(message).queue();
        }
    }
}
