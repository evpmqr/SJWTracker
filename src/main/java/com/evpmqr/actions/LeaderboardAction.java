package com.evpmqr.actions;

import com.evpmqr.App;
import com.evpmqr.objects.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class LeaderboardAction extends Action {

    public LeaderboardAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        List<User> list = App.dataHandler.getUsers().getUserList();
        list.sort((o1, o2) -> o2.getSjwPoints() - o1.getSjwPoints());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SJW Leaderboard\n");
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(i + 1).append(". ").append(list.get(i).getName()).append(" Points: ").append(list.get(i).getSjwPoints()).append("\n");
        }
        sendMessage(stringBuilder.toString(), event);
    }
}
