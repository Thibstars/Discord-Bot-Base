package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.util.concurrent.Callable;
import net.dv8tion.jda.api.events.Event;
import picocli.CommandLine.Command;

/**
 * Command definition providing execution context.
 *
 * @author Thibault Helsmoortel
 */
@Command(mixinStandardHelpOptions = true, versionProvider = VersionProvider.class)
public abstract class BotCommand implements Callable {

    private Event event;

    protected Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
