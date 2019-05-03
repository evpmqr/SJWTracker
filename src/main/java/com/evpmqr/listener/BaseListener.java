package com.evpmqr.listener;

import com.evpmqr.actions.Action;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseListener extends ListenerAdapter {

    private List<Action> actionList = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        for (Action action : actionList) {
            if (!event.getAuthor().isBot() && message.startsWith(action.getCommand())) {
                action.execute(event);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

}
