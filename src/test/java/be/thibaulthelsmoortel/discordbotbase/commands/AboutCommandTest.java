/*
 * Copyright (c) 2019 Thibault Helsmoortel.
 *
 * This file is part of Discord Bot Base.
 *
 * Discord Bot Base is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
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
 *
 */

package be.thibaulthelsmoortel.discordbotbase.commands;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
class AboutCommandTest extends BaseTest {

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @DisplayName("Should reply mystery.")
    @Test
    void shouldReplyMystery() {
        DiscordBotEnvironment environment = mock(DiscordBotEnvironment.class);
        when(environment.getName()).thenReturn(null);
        when(environment.getAuthor()).thenReturn(null);
        AboutCommand command = new AboutCommand(environment);
        command.setEvent(messageReceivedEvent);
        String message = (String) command.call();

        Assertions.assertEquals("Mystery bot by mystery author.", message, "Message should be correct.");

        verifyOneMessageSent(message);
    }

    private void verifyOneMessageSent(String message) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(message);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

    @DisplayName("Should reply about message.")
    @Test
    void shouldReplyAboutMessage() {
        DiscordBotEnvironment environment = mock(DiscordBotEnvironment.class);
        String name = "myBot";
        when(environment.getName()).thenReturn(name);
        String author = "myAuthor";
        when(environment.getAuthor()).thenReturn(author);
        String description = "my bot is the best";
        when(environment.getDescription()).thenReturn(description);

        AboutCommand command = new AboutCommand(environment);
        command.setEvent(messageReceivedEvent);

        String message = (String) command.call();

        Assertions.assertEquals(name + " created by " + author + "." + System.lineSeparator() + description, message, "Message should be correct.");

        verifyOneMessageSent(message);
    }

    @DisplayName("Should reply about message without bot name.")
    @Test
    void shouldReplyAboutMessageWithoutBotName() {
        DiscordBotEnvironment environment = mock(DiscordBotEnvironment.class);
        when(environment.getName()).thenReturn(null);
        String author = "myAuthor";
        when(environment.getAuthor()).thenReturn(author);
        String description = "my bot is the best";
        when(environment.getDescription()).thenReturn(description);

        AboutCommand command = new AboutCommand(environment);
        command.setEvent(messageReceivedEvent);

        String message = (String) command.call();

        Assertions.assertEquals("Bot created by " + author + "." + System.lineSeparator() + description, message, "Message should be correct.");

        verifyOneMessageSent(message);
    }

}
