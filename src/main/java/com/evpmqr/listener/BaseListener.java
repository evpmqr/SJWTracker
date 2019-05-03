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
            if (message.startsWith(action.getCommand())) {
                action.execute(event);
            }
        }
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

}
