package be.thibaulthelsmoortel.discordbotbase.config;

import be.thibaulthelsmoortel.discordbotbase.commands.core.BotCommand;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

/**
 * Configuration preparing commands.
 *
 * @author Thibault Helsmoortel
 */
@Configuration
public class CommandsConfig {

    private final ListableBeanFactory listableBeanFactory;

    @Autowired
    public CommandsConfig(ListableBeanFactory listableBeanFactory) {
        this.listableBeanFactory = listableBeanFactory;
    }

    @Bean
    public List<BotCommand> commands() {
        Map<String, Object> commands = listableBeanFactory.getBeansWithAnnotation(CommandLine.Command.class);

        return commands.entrySet().stream()
            .filter(entry -> entry.getValue() instanceof BotCommand)
            .map(entry -> (BotCommand) entry.getValue())
            .collect(Collectors.toList());
    }

}
