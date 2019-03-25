package be.thibaulthelsmoortel.discordbotbase.commands;

import be.thibaulthelsmoortel.discordbotbase.commands.candidates.PermissionCandidates;
import be.thibaulthelsmoortel.discordbotbase.commands.converters.PermissionConverter;
import be.thibaulthelsmoortel.discordbotbase.commands.core.BotCommand;
import net.dv8tion.jda.bot.JDABot;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Command providing an invitation url for the bot.
 *
 * @author Thibault Helsmoortel
 */
@Command(name = "invite", description = "Provides an invitation url for the bot.")
@Component
public class InviteCommand extends BotCommand {

    @SuppressWarnings("MismatchedReadAndWriteOfArray") // Written to via Picocli
    @Option(names = {"-p", "--permission"}, description = "Target bot permission.", arity = "0..1")
    private boolean[] permissionsRequested = new boolean[0];

    @Parameters(paramLabel = "PERMISSION", description = "Target bot permissions. Candidates: ${COMPLETION-CANDIDATES}", arity = "0..*",
        converter = PermissionConverter.class, completionCandidates = PermissionCandidates.class)
    private Permission[] permissions;

    @Override
    public Object call() {
        String message = null;
        if (getEvent() instanceof GenericMessageEvent) {
            JDABot jdaBot = getEvent().getJDA().asBot();

            if (permissionsRequested.length > 0 && permissions != null && permissions.length > 0) {
                message = jdaBot.getInviteUrl(permissions);
            } else {
                message = jdaBot.getInviteUrl(Permission.EMPTY_PERMISSIONS);
            }

            ((GenericMessageEvent) getEvent()).getChannel().sendMessage(message).queue();
        }

        reset();

        return message;
    }

    private void reset() {
        permissionsRequested = new boolean[0];
        permissions = null;
    }
}
