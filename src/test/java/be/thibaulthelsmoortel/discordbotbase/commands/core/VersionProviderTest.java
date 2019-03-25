package be.thibaulthelsmoortel.discordbotbase.commands.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        Assertions.assertTrue(StringUtils.isNotBlank(version), "Version must not be blank.");
    }

    @DisplayName("Should return actual implementation version.")
    @Test
    void shouldReturnActualImplementationVersion() {
        Package pack = mock(Package.class);
        String version = "anActualVersion";
        when(pack.getImplementationVersion()).thenReturn(version);
        versionProvider.setPack(pack);

        String returnedVersion = Arrays.toString(versionProvider.getVersion());

        Assertions.assertTrue(StringUtils.isNotBlank(returnedVersion), "Version must not be blank.");
        Assertions.assertEquals(Arrays.toString(new String[]{version}), returnedVersion, "Versions must match.");
    }
}
