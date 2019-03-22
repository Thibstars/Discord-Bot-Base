package be.thibaulthelsmoortel.discordbotbase.commands.core;

import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.junit.jupiter.params.ParameterizedTest.INDEX_PLACEHOLDER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import java.io.PrintStream;
import java.util.stream.Stream;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
class MessageChannelOutputStreamTest extends BaseTest {

    private MessageChannelOutputStream messageChannelOutputStream;

    @Mock
    private MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        messageChannelOutputStream = new MessageChannelOutputStream();
        messageChannelOutputStream.setMessageChannel(messageChannel);

        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @DisplayName("Should write message to channel.")
    @Test
    void shouldWriteMessageToChannel() {
        String message = "Hello World!";
        messageChannelOutputStream.write(message.getBytes(), 0, message.length());

        verify(messageChannel).sendMessage(message);
    }

    @DisplayName("Should not write blank message to channel.")
    @ParameterizedTest(name = INDEX_PLACEHOLDER + ": " + ARGUMENTS_PLACEHOLDER)
    @MethodSource("blankStrings")
    void shouldNotWriteBlankMessageToChannel(String message) {
        messageChannelOutputStream.write(message != null ? message.getBytes() : null, 0, message != null ? message.length() : 0);

        verifyNoMoreInteractions(messageChannel);
    }

    static Stream<String> blankStrings() {
        return Stream.of("", "   ", null);
    }
}
