/*
 * Copyright (c) 2019 Thibault Helsmoortel.
 *
 * This file is part of Discord Bot Base.
 *
 * Discord Bot Base is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord Bot Base is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Discord Bot Base.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package be.thibaulthelsmoortel.discordbotbase.commands;

import com.github.thibstars.chatbotengine.cli.candidates.discord.PermissionCandidates;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import com.github.thibstars.chatbotengine.cli.converters.discord.PermissionConverter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
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
public class InviteCommand extends BaseCommand<MessageReceivedEvent, Object> {

    @Option(names = {"-p", "--permission"}, description = "Target bot permission.", arity = "0..1")
    private boolean[] permissionsRequested = new boolean[0];

    @Parameters(paramLabel = "PERMISSION", description = "Target bot permissions. Candidates: ${COMPLETION-CANDIDATES}", arity = "0..*",
        converter = PermissionConverter.class, completionCandidates = PermissionCandidates.class)
    private Permission[] permissions;

    @Override
    public Object call() {
        String message = null;
        if (getContext() != null) {
            JDA jda = getContext().getJDA();

            if (permissionsRequested.length > 0 && permissions != null && permissions.length > 0) {
                message = jda.getInviteUrl(permissions);
            } else {
                message = jda.getInviteUrl(Permission.EMPTY_PERMISSIONS);
            }

            getContext().getChannel().sendMessage(message).queue();
        }

        reset();

        return message;
    }

    // Visible for testing
    void setPermissionsRequested(boolean[] permissionsRequested) {
        this.permissionsRequested = permissionsRequested;
    }

    // Visible for testing
    void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }

    private void reset() {
        permissionsRequested = new boolean[0];
        permissions = null;
    }
}
