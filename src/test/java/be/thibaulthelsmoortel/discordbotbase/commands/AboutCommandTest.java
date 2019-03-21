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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thibault Helsmoortel
 */
class AboutCommandTest extends BaseTest {

    private final AboutCommand aboutCommand;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private MessageChannel messageChannel;

    @Autowired
    AboutCommandTest(AboutCommand aboutCommand) {
        this.aboutCommand = aboutCommand;
    }

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @Disabled
    @DisplayName("Should reply mystery.")
    @Test
    void shouldReplyMystery() {
        //aboutCommand.execute(messageReceivedEvent, null);

        verifyOneMessageSent("Mystery bot by mystery author.");
    }

    @Disabled
    @DisplayName("Should reply about message.")
    @Test
    void shouldReplyAboutMessage() {
        DiscordBotEnvironment environment = mock(DiscordBotEnvironment.class);
        String name = "myBot";
        when(environment.getName()).thenReturn(name);
        String author = "myAuthor";
        when(environment.getAuthor()).thenReturn(author);

        AboutCommand command = new AboutCommand(environment);

        //command.execute(messageReceivedEvent, null);

        verifyOneMessageSent(name + " created by " + author + ".");
    }

    private void verifyOneMessageSent(String message) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(message);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }
}
