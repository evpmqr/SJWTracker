package com.evpmqr.actions;

import com.evpmqr.data.DataHandler;
import com.evpmqr.objects.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class LeaderboardAction extends Action {
    private DataHandler dataHandler;

    public LeaderboardAction(String command, DataHandler dataHandler) {
        super(command);
        this.dataHandler = dataHandler;
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        List<User> list = dataHandler.getUsers().getUserList();
        list.sort((o1, o2) -> o2.getSjwPoints() - o1.getSjwPoints());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(i + 1).append(". ").append(list.get(i).getName()).append(" Points: ").append(list.get(i).getSjwPoints()).append("\n");
        }
        sendMessage(stringBuilder.toString(), event);
    }
}
