package be.thibaulthelsmoortel.discordbotbase.commands.converters;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import net.dv8tion.jda.core.Permission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Thibault Helsmoortel
 */
class PermissionConverterTest extends BaseTest {

    private PermissionConverter permissionConverter;

    @BeforeEach
    void setUp() {
        this.permissionConverter = new PermissionConverter();
    }

    @DisplayName("Should convert permission.")
    @Test
    void shouldConvertPermission() {
        Permission permissionToConvert = Permission.MESSAGE_READ;
        Permission permission = permissionConverter.convert(permissionToConvert.toString());

        Assertions.assertEquals(permissionToConvert, permission, "Permission should be converted correctly.");
    }

    @DisplayName("Should not convert unexpected value.")
    @Test
    void shouldNotConvertUnexpectedValue() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> permissionConverter.convert("someUnexpectedValue"),
            "Conversion should fail.");
    }
}
