package be.thibaulthelsmoortel.discordbotbase.commands.core;

import picocli.CommandLine.IVersionProvider;

/**
 * Provides the implementation version of the current package.
 *
 * @author Thibault Helsmoortel
 */
public class VersionProvider implements IVersionProvider {

    // The version number is populated on packaging phase, so it will be unknown while testing/developing
    private static final String VERSION_UNKNOWN = "unknown";

    @Override
    public String[] getVersion() {
        String implementationVersion = getClass().getPackage().getImplementationVersion();
        return new String[]{implementationVersion != null ? implementationVersion : VERSION_UNKNOWN};
    }
}
