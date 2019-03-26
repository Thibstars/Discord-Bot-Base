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
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * Command thanking the bot for being helpful.
 *
 * @author Thibault Helsmoortel
 */
@Command(name = "good bot", description = "Allows you to thank the bot for being helpful.")
@Component
public class GoodBotCommand extends BotCommand {

    private static final String THUMBS_UP_EMOJI = "\uD83D\uDC4D";

    @Override
    public Object call() {
        String emoji = null;
        if (getEvent() instanceof MessageReceivedEvent) {
            Message message = ((MessageReceivedEvent) getEvent()).getMessage();
            emoji = THUMBS_UP_EMOJI;
            message.addReaction(emoji).queue();
        }

        return emoji;
    }
}
