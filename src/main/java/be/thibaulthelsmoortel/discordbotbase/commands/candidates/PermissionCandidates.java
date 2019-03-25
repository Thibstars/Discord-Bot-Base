package be.thibaulthelsmoortel.discordbotbase.commands.candidates;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.dv8tion.jda.core.Permission;

/**
 * List of all existing {@link Permission}s.
 *
 * @author Thibault Helsmoortel
 */
public class PermissionCandidates extends ArrayList<String> {

    PermissionCandidates() {
        super(Stream.of(Permission.values()).map(Enum::toString).collect(Collectors.toList()));
    }
}
