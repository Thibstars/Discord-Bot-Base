package be.thibaulthelsmoortel.discordbotbase.config;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
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

    private final List commands;

    @Autowired
    CommandsConfigTest(List commands) {
        this.commands = commands;
    }

    @DisplayName("Should properly assemble commands.")
    @Test
    void shouldProperlyAssembleCommands() {
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commands), "Commands must not be empty.");
    }
}
