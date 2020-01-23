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

package be.thibaulthelsmoortel.discordbotbase.application;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.cli.commands.CommandExecutor;
import com.github.thibstars.chatbotengine.cli.io.discord.MessageChannelOutputStream;
import com.github.thibstars.chatbotengine.provider.discord.DiscordProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component running the discord bot.
 *
 * @author Thibault Helsmoortel
 */
@Component
@RequiredArgsConstructor
public class DiscordBotRunner extends ListenerAdapter implements CommandLineRunner {

    private final DiscordBotEnvironment discordBotEnvironment;
    private final CommandExecutor commandExecutor;

    private final DiscordProvider discordProvider;
    private final DiscordTokenAuthentication discordTokenAuthentication;
    private final MessageChannelOutputStream messageChannelOutputStream;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (processMessage(message)) {
            String msg = message.getContentDisplay();
            handleMessage(event, msg);
        }
    }

    private boolean processMessage(Message message) {
        return (discordBotEnvironment.isProcessBotMessages() && message.getAuthor().isBot()) || !message.getAuthor().isBot();
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        TextChannel textChannel = Objects.requireNonNull(event.getGuild().getDefaultChannel());
        textChannel.sendTyping().queue();
        textChannel.sendMessage(discordBotEnvironment.getName() + " reporting for duty!").queue();
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(discordBotEnvironment.getCommandPrefix())) {
            event.getChannel().sendTyping().queue();

            String parsedMessage = msg.substring(discordBotEnvironment.getCommandPrefix().length());
            messageChannelOutputStream.setMessageChannel(event.getChannel());
            commandExecutor.tryExecute(event, parsedMessage, () -> event.getChannel().sendMessage("Command not recognized...").queue(), () -> event.getChannel().sendMessage("Command failed!").queue());
        }
    }

    @Override
    public void run(String... args) {
        String token;
        if (StringUtils.isNotBlank(discordBotEnvironment.getToken())) {
            token = discordBotEnvironment.getToken();
        } else {
            // Take token as first run arg (for example for when running from docker with an ENV variable)
            if (args != null && args.length > 0) {
                token = args[0];
            } else {
                token = null;
            }
        }

        JDABuilder jdaBuilder = ((DiscordTokenAuthenticationHandler) discordTokenAuthentication.getHandler()).getJdaBuilder();
        jdaBuilder.addEventListeners(this);
        discordTokenAuthentication.setToken(token);
        discordProvider.authenticate();

    }
}
