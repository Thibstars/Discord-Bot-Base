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

package be.thibaulthelsmoortel.discordbotbase.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.cli.commands.CommandExecutor;
import com.github.thibstars.chatbotengine.cli.io.discord.MessageChannelOutputStream;
import com.github.thibstars.chatbotengine.provider.discord.DiscordProvider;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.entities.ReceivedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
class DiscordBotRunnerTest extends BaseTest {

    private DiscordBotRunner discordBotRunner;

    @Mock
    private DiscordBotEnvironment discordBotEnvironment;

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private User user;

    @Mock
    private DiscordProvider discordProvider;

    @Mock
    private DiscordTokenAuthentication discordTokenAuthentication;

    @Mock
    private MessageChannelOutputStream messageChannelOutputStream;

    @BeforeEach
    void setUp() {
        this.discordBotRunner = new DiscordBotRunner(discordBotEnvironment, commandExecutor, discordProvider, discordTokenAuthentication,
            messageChannelOutputStream);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("Should handle message received.")
    @Test
    void shouldHandleMessageReceived() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendTyping()).thenReturn(mock(RestAction.class));
        ReceivedMessage receivedMessage = mock(ReceivedMessage.class);
        when(messageReceivedEvent.getMessage()).thenReturn(receivedMessage);
        when(receivedMessage.getAuthor()).thenReturn(user);
        when(user.isBot()).thenReturn(false);

        String prefix = "/";
        String message = "myNewMessage";
        when(receivedMessage.getContentDisplay()).thenReturn(prefix + message);
        when(discordBotEnvironment.getCommandPrefix()).thenReturn(prefix);

        discordBotRunner.onMessageReceived(messageReceivedEvent);

        verify(messageChannel).sendTyping();
        verifyNoMoreInteractions(messageChannel);
        verify(commandExecutor).tryExecute(eq(messageReceivedEvent), eq(message), any(Runnable.class), any(Runnable.class));
        verifyNoMoreInteractions(commandExecutor);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("Should send message on guild ready.")
    @Test
    void shouldSendMessageOnGuildReady() {
        GuildReadyEvent event = mock(GuildReadyEvent.class);
        Guild guild = mock(Guild.class);
        when(event.getGuild()).thenReturn(guild);
        TextChannel textChannel = mock(TextChannel.class);
        when(guild.getDefaultChannel()).thenReturn(textChannel);
        when(textChannel.sendTyping()).thenReturn(mock(RestAction.class));
        when(textChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));

        discordBotRunner.onGuildReady(event);

        verify(textChannel).sendTyping();
        verify(textChannel).sendMessage(discordBotEnvironment.getName() + " reporting for duty!");
        verifyNoMoreInteractions(textChannel);
    }

    @DisplayName("Should not process bot messages.")
    @Test
    void shouldNotProcessBotMessages() {
        configureAsBot();
        when(discordBotEnvironment.isProcessBotMessages()).thenReturn(false);

        discordBotRunner.onMessageReceived(messageReceivedEvent);

        verifyNoMoreInteractions(messageChannel);
        verify(messageReceivedEvent).getMessage(); // 1 to check processing
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("Should process bot messages.")
    @Test
    void shouldProcessBotMessages() {
        Message messageMock = configureAsBot();
        when(discordBotEnvironment.isProcessBotMessages()).thenReturn(true);

        String prefix = "/";
        String message = "myNewMessage";
        when(messageMock.getContentDisplay()).thenReturn(prefix + message);
        when(discordBotEnvironment.getCommandPrefix()).thenReturn(prefix);
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendTyping()).thenReturn(mock(RestAction.class));

        discordBotRunner.onMessageReceived(messageReceivedEvent);

        verify(messageChannel).sendTyping();
        verifyNoMoreInteractions(messageChannel);
        verify(commandExecutor).tryExecute(eq(messageReceivedEvent), eq(message), any(Runnable.class), any(Runnable.class));
        verifyNoMoreInteractions(commandExecutor);
    }

    private Message configureAsBot() {
        Message messageMock = mock(Message.class);
        when(messageReceivedEvent.getMessage()).thenReturn(messageMock);
        when(messageMock.getAuthor()).thenReturn(user);
        when(user.isBot()).thenReturn(true);

        return messageMock;
    }
}
