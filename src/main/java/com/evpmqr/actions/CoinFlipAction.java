package com.evpmqr.actions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class CoinFlipAction extends Action {

    public CoinFlipAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        boolean i = new Random().nextBoolean();
        String result = (i) ? "heads" : "tails";
        sendMessage(result, event);
    }
}
