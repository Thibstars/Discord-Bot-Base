package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.util.concurrent.Callable;
import net.dv8tion.jda.core.events.Event;

/**
 * BotCommand definition providing execution context.
 *
 * @author Thibault Helsmoortel
 */
public abstract class BotCommand implements Callable {

    protected Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
