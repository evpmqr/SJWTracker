package com.evpmqr.actions;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public abstract class Action {
    private String command;

    public Action(String command) {
        this.command = command;
    }

    protected void sendMessage(String message, GuildMessageReceivedEvent event) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append(message);
        event.getChannel().sendMessage(messageBuilder.build()).queue();
    }

    public String getCommand() {
        return command;
    }

    public abstract void execute(GuildMessageReceivedEvent event);
}
