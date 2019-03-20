package be.thibaulthelsmoortel.discordbotbase.config;

import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_PLACEHOLDER;
import static org.junit.jupiter.params.ParameterizedTest.INDEX_PLACEHOLDER;
import static org.mockito.Mockito.mock;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import be.thibaulthelsmoortel.discordbotbase.exceptions.MissingTokenException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Thibault Helsmoortel
 */
class DiscordBotEnvironmentTest extends BaseTest {

    @DisplayName("Should throw MissingTokenException.")
    @ParameterizedTest(name = INDEX_PLACEHOLDER + ": " + ARGUMENTS_PLACEHOLDER)
    @MethodSource("blankStrings")
    void shouldThrowMissingTokenException(String token) {
        DiscordBotEnvironment env = new DiscordBotEnvironment();
        env.setToken(token);

        Assertions.assertThrows(MissingTokenException.class, env::afterPropertiesSet, "Exception should be thrown when no token was provided.");
    }

    @DisplayName("Should not throw MissingTokenException.")
    @Test
    void shouldNotThrowMissingTokenException() {
        // Mocking the object does not result in a blank token
        DiscordBotEnvironment env = mock(DiscordBotEnvironment.class);
        env.afterPropertiesSet();
    }

    static Stream<String> blankStrings() {
        return Stream.of("", "   ", null);
    }

}
