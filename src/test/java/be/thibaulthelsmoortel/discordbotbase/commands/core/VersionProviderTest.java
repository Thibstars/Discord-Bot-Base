package be.thibaulthelsmoortel.discordbotbase.commands.core;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author Thibault Helsmoortel
 */
class VersionProviderTest extends BaseTest {

    private VersionProvider versionProvider;

    @BeforeEach
    void setUp() {
        this.versionProvider = new VersionProvider();
    }

    @DisplayName("Should return implementation version.")
    @Test
    void shouldReturnImplementationVersion() {
        String version = Arrays.toString(versionProvider.getVersion());

        Assertions.assertTrue(StringUtils.isNotBlank(version));
    }
}
