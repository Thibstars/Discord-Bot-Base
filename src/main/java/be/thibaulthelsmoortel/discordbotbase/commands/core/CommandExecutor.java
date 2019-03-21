package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class responsible for command execution.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class CommandExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);

    private final List<Command> commands;

    @Autowired
    public CommandExecutor(List<Command> commands) {
        this.commands = commands;
    }

    /**
     * Tries to execute a command.
     *
     * @param event the raised JDA event
     * @param commandMessage the command message (stripped from its prefix)
     * @return true if the command was executed, false if otherwise
     */
    public boolean tryExecute(MessageReceivedEvent event, String commandMessage) {
        AtomicBoolean commandRecognised = new AtomicBoolean(false);

        if (StringUtils.isNotBlank(commandMessage)) {
            commands.forEach(command -> {
                CommandType commandType = command.getClass().getAnnotation(CommandType.class);
                String commandName = commandType.name();

                if (commandMessage.startsWith(commandName)) {
                    if (commandType.strategy() == ParameterStrategy.DISABLED) {
                        commandRecognised.set(true);
                        command.execute(event, null);
                    } else if (commandType.strategy() == ParameterStrategy.ENABLED) {
                        commandRecognised.set(true);
                        String parameters = commandMessage.substring(commandMessage.indexOf(commandType.name()) + commandType.name().length());
                        command.execute(event, Arrays.asList(parameters.split(" ")));
                    }
                }
            });

            if (commandRecognised.get()) {
                LOGGER.debug("Executed command: {}.", commandMessage);
            } else {
                LOGGER.debug("Command not recognized: {}.", commandMessage);
                event.getChannel().sendMessage("Command not recognized...").queue();
            }
        }

        return commandRecognised.get();
    }

}
