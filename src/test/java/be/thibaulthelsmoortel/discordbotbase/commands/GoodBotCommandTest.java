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
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thibault Helsmoortel
 */
class GoodBotCommandTest extends BaseTest {

    private final GoodBotCommand goodBotCommand;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private Message message;

    @Autowired
    GoodBotCommandTest(GoodBotCommand goodBotCommand) {
        this.goodBotCommand = goodBotCommand;
    }

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getMessage()).thenReturn(message);
        when(message.addReaction(anyString())).thenReturn(mock(RestAction.class));
        goodBotCommand.setEvent(messageReceivedEvent);
    }

    @DisplayName("Should add reaction to message.")
    @Test
    void shouldAddReactionToMessage() {
        String emoji = (String) goodBotCommand.call();

        Assertions.assertTrue(StringUtils.isNotBlank(emoji), "Emoji must not be blank.");

        verify(messageReceivedEvent).getMessage();
        verify(message).addReaction(emoji);
    }
}
