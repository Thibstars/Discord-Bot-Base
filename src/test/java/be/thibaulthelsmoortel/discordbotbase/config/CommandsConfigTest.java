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

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thibault Helsmoortel
 */
class CommandsConfigTest extends BaseTest {

    private final List<BaseCommand> commands;

    @Autowired
    CommandsConfigTest(List<BaseCommand> commands) {
        this.commands = commands;
    }

    @DisplayName("Should properly assemble commands.")
    @Test
    void shouldProperlyAssembleCommands() {
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commands), "Commands must not be empty.");
    }
}
