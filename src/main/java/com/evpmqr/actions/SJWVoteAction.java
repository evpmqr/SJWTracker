package com.evpmqr.actions;

import com.evpmqr.App;
import com.evpmqr.objects.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SJWVoteAction extends Action {

    public SJWVoteAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        String[] split = message.split("\\s+");

        String name = split[1].toLowerCase();
        String op = (message.contains("+")) ? "+" : "-";

        int amount = (op.equals("+")) ? 1 : -1;

        System.out.println(name);
        System.out.println(amount);

        User user = App.dataHandler.fetchUser(name);
        if (user != null) {
            System.out.println(user.getName());
            int newAmount = user.getSjwPoints() + amount;
            newAmount = (newAmount < 0) ? 0 : newAmount;
            user.setSjwPoints(newAmount);
            App.dataHandler.saveData();
        }
    }
}
