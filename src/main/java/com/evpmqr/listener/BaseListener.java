package com.evpmqr.listener;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BaseListener extends ListenerAdapter {

    protected void sendMessage(String message, GuildMessageReceivedEvent event) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.append(message);
        event.getChannel().sendMessage(messageBuilder.build()).queue();
    }
}
