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

import be.thibaulthelsmoortel.discordbotbase.commands.core.CommandExecutor;
import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import java.util.Objects;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component running the discord bot.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class DiscordBotRunner extends ListenerAdapter implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordBotRunner.class);

    private final DiscordBotEnvironment discordBotEnvironment;
    private final CommandExecutor commandExecutor;

    @Autowired
    public DiscordBotRunner(DiscordBotEnvironment discordBotEnvironment,
        CommandExecutor commandExecutor) {
        this.discordBotEnvironment = discordBotEnvironment;
        this.commandExecutor = commandExecutor;
    }

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

            commandExecutor.tryExecute(event, parsedMessage);
        }
    }

    @Override
    public void run(String... args) {
        try {
            new JDABuilder(AccountType.BOT)
                .setToken(discordBotEnvironment.getToken())
                .addEventListener(this)
                .build()
                .awaitReady();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
