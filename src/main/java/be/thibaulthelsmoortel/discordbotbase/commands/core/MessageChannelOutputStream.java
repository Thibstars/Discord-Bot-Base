package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.io.OutputStream;
import java.io.PrintStream;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * OutputStream able to write to a set message channel.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class MessageChannelOutputStream extends OutputStream {

    private MessageChannel messageChannel;

    @Override
    public void write(int b) {
        // It's tempting to use writer.write((char) b), but that may get the encoding wrong
        // This is inefficient, but it works
        write(new byte[] {(byte) b}, 0, 1);
    }

    @Override
    public void write(byte b[], int off, int len) {
        String content = b != null ? new String(b, off, len) : null;
        if (StringUtils.isNotBlank(content)) {
            messageChannel.sendMessage(content).queue();
        }
    }

    public MessageChannel getMessageChannel() {
        return messageChannel;
    }

    public void setMessageChannel(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }
}