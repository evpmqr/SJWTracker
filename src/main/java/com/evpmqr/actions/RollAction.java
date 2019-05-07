package com.evpmqr.actions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class RollAction extends Action {

    public RollAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        //"!roll 100"
        String[] result = message.split("\\s+");
        int size = Integer.parseInt(result[1]);
        int answer = new Random().nextInt(size);
        sendMessage("" + answer, event);
    }
}
