package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

import java.util.Arrays;
import java.util.List;

public class CommandContext {

    private final IDiscordClient client;
    private final String command;
    private final List<String> args;
    private final IMessage message;
    private final IGuild guild;
    private final IChannel channel;
    private final IUser author;

    public CommandContext(MessageReceivedEvent event, String command, String args) {
        this.client = event.getClient();
        this.command = command;
        this.args = Arrays.asList(args.split("\\s+"));
        this.message = event.getMessage();
        this.guild = event.getGuild();
        this.channel = event.getChannel();
        this.author = event.getAuthor();
    }

    public IDiscordClient getClient() {
        return client;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }

    public IMessage getMessage() {
        return message;
    }

    public IGuild getGuild() {
        return guild;
    }

    public IChannel getChannel() {
        return channel;
    }

    public IUser getAuthor() {
        return author;
    }
}
