package be.thibaulthelsmoortel.discordbotbase.commands.converters;

import net.dv8tion.jda.core.Permission;
import picocli.CommandLine.ITypeConverter;

/**
 * String to {@link Permission} converter.
 *
 * @author Thibault Helsmoortel
 */
public class PermissionConverter implements ITypeConverter<Permission> {

    @Override
    public Permission convert(String value) {
        return Permission.valueOf(value);
    }
}
