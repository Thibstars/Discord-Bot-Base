package be.thibaulthelsmoortel.discordbotbase.application;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import be.thibaulthelsmoortel.discordbotbase.commands.core.CommandExecutor;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.ReceivedMessage;
import net.dv8tion.jda.core.events.guild.GuildReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
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

    @BeforeEach
    void setUp() {
        this.discordBotRunner = new DiscordBotRunner(discordBotEnvironment, commandExecutor);
    }

    @DisplayName("Should handle message received.")
    @Test
    void shouldHandleMessageReceived() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendTyping()).thenReturn(mock(RestAction.class));
        ReceivedMessage receivedMessage = mock(ReceivedMessage.class);
        when(messageReceivedEvent.getMessage()).thenReturn(receivedMessage);
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
        verify(commandExecutor).tryExecute(messageReceivedEvent, message);
        verifyNoMoreInteractions(commandExecutor);
    }

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

    @DisplayName("Should skip bot messages.")
    @Test
    void shouldSkipBotMessages() {
        Message messageMock = mock(Message.class);
        when(messageReceivedEvent.getMessage()).thenReturn(messageMock);
        when(messageMock.getAuthor()).thenReturn(user);
        when(user.isBot()).thenReturn(true);

        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }
}
