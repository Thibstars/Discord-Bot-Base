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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.Command;

/**
 * @author Thibault Helsmoortel
 */
class HelpCommandTest extends CommandBaseTest {

    private List<BaseCommand> baseCommands;

    @DisplayName("Should send help message.")
    @Test
    void shouldSendHelpMessage() {
        baseCommands = new ArrayList<>();
        baseCommands.add(new InviteCommand());
        baseCommands.add(mock(HelpCommand.class));

        DiscordBotEnvironment environment = mock(DiscordBotEnvironment.class);
        when(environment.getCommandPrefix()).thenReturn("/");
        HelpCommand command = new HelpCommand(environment, baseCommands);
        command.setContext(messageReceivedEvent);

        when(messageChannel.getType()).thenReturn(ChannelType.UNKNOWN);
        when(messageChannel.getId()).thenReturn("id");
        MessageAction messageAction = mock(MessageAction.class);
        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(messageAction);

        MessageEmbed embedded = (MessageEmbed) command.call();
        Assertions.assertNotNull(embedded, "Message must not be null.");
        Assertions.assertTrue(StringUtils.isNotBlank(embedded.getDescription()), "Description should not be empty.");
        Assertions.assertNotNull(embedded.getFields(), "Message fields must not be null.");
        Assertions.assertFalse(embedded.getFields().isEmpty(), "Message fields must not be empty.");
        baseCommands.forEach(baseCommand -> {
            if (!(baseCommand instanceof HelpCommand)) {
                Command annotation = baseCommand.getClass().getAnnotation(Command.class);
                Assertions.assertTrue(embedded.getFields().stream().anyMatch(field -> Objects.equals(field.getName(), annotation.name())),
                    "Message should contain command name.");
                Assertions.assertTrue(embedded.getFields().stream().anyMatch(field -> Objects.equals(field.getValue(), parseDescription(annotation))),
                    "Message should contain command description.");
            }
        });

        verifyOneMessageSent(messageAction);
    }

    void verifyOneMessageSent(MessageAction messageAction) {
        verify(messageAction).queue();
    }

    private String parseDescription(Command annotation) {
        String array = Arrays.toString(annotation.description());
        return array.substring(1, array.length() - 1);
    }

    @DisplayName("Should not process event.")
    @Test
    void shouldNotProcessEvent() throws Exception {
        HelpCommand command = new HelpCommand(mock(DiscordBotEnvironment.class), baseCommands);

        verifyDoNotProcessEvent(command, null);
    }

}
