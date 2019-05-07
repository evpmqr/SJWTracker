package com.evpmqr.actions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class CoinFlipAction extends Action {

    public CoinFlipAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        int i = new Random().nextInt(1);
        String result = (i == 0) ? "heads" : "tails";
        sendMessage(result, event);
    }
}
