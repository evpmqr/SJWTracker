package com.evpmqr.actions;

import com.evpmqr.App;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class QueueTriviaAction extends Action {

    public QueueTriviaAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (!App.listening) {
            App.listening = true;
            if (App.triviaHandler.getTrivia() == null || App.triviaHandler.getTrivia().getTriviaResults().isEmpty()) {
                App.triviaHandler.getQuestions();
            }
            App.triviaHandler.setQuestion();
            sendMessage(App.triviaHandler.getTriviaResult().formatQuestion(), event);
        }
    }
}
