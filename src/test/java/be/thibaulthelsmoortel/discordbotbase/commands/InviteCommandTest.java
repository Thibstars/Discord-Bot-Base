package be.thibaulthelsmoortel.discordbotbase.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import be.thibaulthelsmoortel.discordbotbase.BaseTest;
import net.dv8tion.jda.bot.JDABot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thibault Helsmoortel
 */
class InviteCommandTest extends BaseTest {

    private static final String EXPECTED_SCOPE = "scope=bot";
    private static final String PERMISSIONS_PARAM = "permissions=";

    private static final String INVITE_URL_WITH_PERMISSIONS = "https://discordapp.com/oauth2/authorize?scope=bot&client_id=446990121802618890&permissions=3072";
    private static final String INVITE_URL_NO_PERMISSIONS = "https://discordapp.com/oauth2/authorize?scope=bot&client_id=446990121802618890";

    private final InviteCommand inviteCommand;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private JDABot jdaBot;

    @Autowired
    InviteCommandTest(InviteCommand inviteCommand) {
        this.inviteCommand = inviteCommand;
    }

    @BeforeEach
    void setUp() {
        JDA jda = mock(JDA.class);
        when(messageReceivedEvent.getJDA()).thenReturn(jda);
        when(jda.asBot()).thenReturn(jdaBot);
        when(jdaBot.getInviteUrl(Permission.EMPTY_PERMISSIONS)).thenReturn(INVITE_URL_NO_PERMISSIONS);
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @DisplayName("Should return invite url without permissions.")
    @Test
    void shouldReturnInviteUrlWithoutPermissions() {
        inviteCommand.setEvent(messageReceivedEvent);

        String message = (String) inviteCommand.call();

        Assertions.assertNotNull(message, "Invite url must not be null.");
        Assertions.assertTrue(message.contains(EXPECTED_SCOPE), "Scope must be correct.");
        Assertions.assertFalse(message.contains(PERMISSIONS_PARAM), "No permissions should be provided.");

        verifyOneMessageSent();
    }

    @DisplayName("Should return invite url with permissions.")
    @Test
    void shouldReturnInviteUrlWithPermissions() {
        when(jdaBot.getInviteUrl(any(Permission[].class))).thenReturn(INVITE_URL_WITH_PERMISSIONS);
        inviteCommand.setEvent(messageReceivedEvent);

        String message = (String) inviteCommand.call();
        Assertions.assertNotNull(message, "Invite url must not be null.");
        Assertions.assertTrue(message.contains(EXPECTED_SCOPE), "Scope must be correct.");
        Assertions.assertTrue(message.contains(PERMISSIONS_PARAM), "Permissions should be provided.");

        verifyOneMessageSent();
    }

    private void verifyOneMessageSent() {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(anyString());
        verifyNoMoreInteractions(messageChannel);
    }
}
