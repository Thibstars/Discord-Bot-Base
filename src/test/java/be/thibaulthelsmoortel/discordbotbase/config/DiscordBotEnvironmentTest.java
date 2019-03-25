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
