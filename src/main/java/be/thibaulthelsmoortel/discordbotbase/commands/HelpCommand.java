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

import be.thibaulthelsmoortel.discordbotbase.commands.core.BotCommand;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * @author Thibault Helsmoortel
 */
@Command(name = "help", description = "Provides command usage help.")
@Component
public class HelpCommand extends BotCommand {

    private static final String NEWLINE = System.lineSeparator();

    private final DiscordBotEnvironment discordBotEnvironment;
    private final List<BotCommand> botCommands;

    @Autowired
    public HelpCommand(DiscordBotEnvironment discordBotEnvironment,
        List<BotCommand> botCommands) {
        this.discordBotEnvironment = discordBotEnvironment;
        this.botCommands = botCommands;
    }

    @Override
    public Object call() {
        String message = null;

        if (getEvent() instanceof MessageReceivedEvent) {
            message = "Usage: " + discordBotEnvironment.getCommandPrefix() + "COMMAND [OPTIONS]" + NEWLINE + NEWLINE;

            message += discordBotEnvironment.getDescription() + NEWLINE + NEWLINE;

            message += createCommandOverview();

            message += NEWLINE + "Run '" + discordBotEnvironment.getCommandPrefix() + "COMMAND --help' for more information on a command.";

            ((MessageReceivedEvent) getEvent()).getChannel().sendMessage(message).queue();
        }

        return message;
    }

    private String createCommandOverview() {
        AtomicReference<String> overview = new AtomicReference<>("Commands:" + NEWLINE);
        botCommands.forEach(botCommand -> {
            if (!(botCommand instanceof HelpCommand)) {
                Command annotation = botCommand.getClass().getAnnotation(Command.class);
                overview.set(overview.get() + String.format("%-15s %s", annotation.name(), parseDescription(annotation)) + NEWLINE);
            }
        });

        return overview.get();
    }

    private String parseDescription(Command annotation) {
        String array = Arrays.toString(annotation.description());
        return array.substring(1, array.length() - 1);
    }
}
