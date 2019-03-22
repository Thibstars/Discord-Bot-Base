package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.util.concurrent.Callable;
import net.dv8tion.jda.core.events.Event;
import picocli.CommandLine.Option;

/**
 * Command definition providing execution context.
 *
 * @author Thibault Helsmoortel
 */
public abstract class BotCommand implements Callable {

    @SuppressWarnings("unused")
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints this help message and exits.")
    private boolean helpRequested;

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
