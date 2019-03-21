package be.thibaulthelsmoortel.discordbotbase.commands.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import be.thibaulthelsmoortel.discordbotbase.commands.AboutCommand;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine.Command;

/**
 * @author Thibault Helsmoortel
 */
class CommandExecutorTest extends BaseTest {

    private final CommandExecutor commandExecutor;

    private final AboutCommand aboutCommand;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private MessageChannel messageChannel;

    @Autowired
    CommandExecutorTest(CommandExecutor commandExecutor, AboutCommand aboutCommand) {
        this.commandExecutor = commandExecutor;
        this.aboutCommand = aboutCommand;
    }

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @DisplayName("Should execute command.")
    @Test
    void shouldExecuteCommand() {
        String commandName = aboutCommand.getClass().getAnnotation(Command.class).name();

        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName);

        // Assuming the command sends a message back:
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(anyString());
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);

        Assertions.assertTrue(executed, "BotCommand should be executed.");
    }

    @DisplayName("Should not execute command.")
    @Test
    void shouldNotExecuteCommand() {
        String commandName = "someUnavailableCommand";

        boolean executed = commandExecutor.tryExecute(messageReceivedEvent, commandName);

        // The executor should send back a message:
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage("BotCommand not recognized...");
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);

        Assertions.assertFalse(executed, "BotCommand should not be executed.");
    }
}
