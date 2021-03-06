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

        User author = App.dataHandler.fetchUser(event.getAuthor().getId());

        if (author.canVote() || author.isAdmin()) {
            String[] split = message.split("\\s+");

            String name = split[1].toLowerCase();
            String op = (message.contains("+")) ? "+" : "-";

            int amount;
            if (author.isAdmin()) {
                if (op.contains("-")) {
                    amount = Integer.parseInt(split[2]);
                } else {
                    String num = split[2].substring(1);
                    amount = Integer.parseInt(num);
                }
            } else {
                amount = (op.equals("+")) ? 1 : -1;
            }
            System.out.println(name);
            System.out.println(amount);

            User user = App.dataHandler.fetchUser(name);
            if (user != null && (!user.getId().equals(author.getId()) || author.isAdmin())) {
                System.out.println(user.getName());
                int newAmount = user.getSjwPoints() + amount;
                newAmount = (newAmount < 0) ? 0 : newAmount;
                user.setSjwPoints(newAmount);
                author.setVotesLeft(author.getVotesLeft() - 1);
                App.dataHandler.saveData();
            }
        } else {
            sendMessage("No more vote points left!\n", event);
        }
    }
}
