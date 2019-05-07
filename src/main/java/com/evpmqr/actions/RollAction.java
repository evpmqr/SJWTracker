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
        if (size < 0) {
            sendMessage(event.getAuthor().getAsMention() + " You are an idiot", event);
        }
        else {
            int answer = new Random().nextInt(size);
            sendMessage("" + answer, event);
        }

    }
}
