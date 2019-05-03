package com.evpmqr.actions;

import com.evpmqr.handlers.DataHandler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ResetVoteAction extends Action {
    private DataHandler dataHandler;

    public ResetVoteAction(String command, DataHandler dataHandler) {
        super(command);
        this.dataHandler = dataHandler;
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        dataHandler.resetPoints();
    }
}
