package com.evpmqr.actions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class IdiotAction extends Action {

    public IdiotAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        boolean boo = new Random().nextBoolean();
        String s = boo ? "You are a idiot" : "You are not a idiot";
        sendMessage(event.getAuthor().getAsMention() + " " + s, event);
    }
}
