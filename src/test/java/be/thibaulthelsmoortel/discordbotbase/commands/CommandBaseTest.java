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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
abstract class CommandBaseTest extends BaseTest {

    @Mock
    MessageReceivedEvent messageReceivedEvent;

    @Mock
    Message message;

    @Mock
    MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageReceivedEvent.getMessage()).thenReturn(message);
        when(message.addReaction(anyString())).thenReturn(mock(RestAction.class));
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void verifyOneMessageSent(String message) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(message);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void verifyOneMessageSent(MessageEmbed embed) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(embed);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

    void verifyDoNotProcessEvent(BaseCommand baseCommand, Event event) throws Exception {
        baseCommand.setContext(event);

        String messageValue = (String) baseCommand.call();

        Assertions.assertNull(messageValue, "Message should not be processed.");
        verifyNoMoreJDAInteractions();
    }

    void verifyNoMoreJDAInteractions() {
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

}