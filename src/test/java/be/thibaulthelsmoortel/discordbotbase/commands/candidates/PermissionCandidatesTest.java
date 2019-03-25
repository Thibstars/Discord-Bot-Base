package be.thibaulthelsmoortel.discordbotbase.commands.candidates;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import net.dv8tion.jda.core.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Thibault Helsmoortel
 */
class PermissionCandidatesTest extends BaseTest {

    @DisplayName("Should return all existing permissions.")
    @Test
    void shouldReturnAllExistingPermissions() {
        new PermissionCandidates().forEach(candidate -> Assertions.assertNotNull(Permission.valueOf(candidate), "Permission must exist."));
    }
}
