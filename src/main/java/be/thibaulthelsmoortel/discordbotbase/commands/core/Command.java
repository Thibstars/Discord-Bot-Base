package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.util.Collection;
import net.dv8tion.jda.core.events.Event;

/**
 * Command definition providing execution context.
 *
 * @author Thibault Helsmoortel
 */
@FunctionalInterface
public interface Command {

    void execute(Event event, Collection<String> parameters);
}
