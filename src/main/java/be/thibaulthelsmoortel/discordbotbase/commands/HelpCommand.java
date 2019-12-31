/*
 * Copyright (c) 2019 Thibault Helsmoortel.
 *
 * This file is part of Discord Bot Base.
 *
 * Discord Bot Base is free software: you can redistribute it and/or modify
 *  t under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord Bot Base is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Discord Bot Base.  If not, see <https://www.gnu.org/licenses/>.
 */

package be.thibaulthelsmoortel.discordbotbase.commands;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * @author Thibault Helsmoortel
 */
@Command(name = "help", description = "Provides command usage help.")
@Component
public class HelpCommand extends BaseCommand<MessageReceivedEvent, Object> {

    private final DiscordBotEnvironment discordBotEnvironment;
    private final List<BaseCommand> baseCommands;

    @Autowired
    public HelpCommand(DiscordBotEnvironment discordBotEnvironment,
        List<BaseCommand> baseCommands) {
        this.discordBotEnvironment = discordBotEnvironment;
        this.baseCommands = baseCommands;
    }

    @Override
    public Object call() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (getContext() != null) {
            StringBuilder descriptionBuilder = embedBuilder.getDescriptionBuilder();
            descriptionBuilder.append("Usage: ").append(discordBotEnvironment.getCommandPrefix()).append("COMMAND [OPTIONS]")
                .append(String.format("%n%n%s%n%n", discordBotEnvironment.getDescription()))
                .append(String.format("%s%n", "Commands:"));

            baseCommands.forEach(baseCommand -> {
                if (!(baseCommand instanceof HelpCommand)) {
                    Command annotation = baseCommand.getClass().getAnnotation(Command.class);
                    embedBuilder.addField(annotation.name(), parseDescription(annotation), false);
                }
            });

            MessageEmbed embed = embedBuilder.build();
            getContext().getChannel().sendMessage(embed).queue();

            return embed;
        }

        return null;
    }

    private String parseDescription(Command annotation) {
        String array = Arrays.toString(annotation.description());
        return array.substring(1, array.length() - 1);
    }
}
