package com.evpmqr.actions;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class HelpAction extends Action {

    public HelpAction(String command) {
        super(command);
    }


    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String stringBuilder = "!leaderboard - Gets SJW Leaderboard\n" +
                "!vote <name> <+/->1 - Adds/Remove SJW Point\n" +
                "!trivia - adds trivia question to queue\n" +
                "!answer <1/2/3/4> - answers trivia question\n";
        sendMessage(stringBuilder, event);
    }


}
