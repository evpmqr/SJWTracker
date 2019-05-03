package com.evpmqr.actions;

import com.evpmqr.App;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AnswerTriviaAction extends Action {

    public AnswerTriviaAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (App.listening) {
            if (App.triviaHandler.validate(message, App.triviaHandler.getTriviaResult(), event)) {
                sendMessage(event.getAuthor().getAsMention() + " is Correct!\n", event);
                App.triviaHandler.updateTriviaPoints(event.getAuthor().getId(), 1);
            } else {
                sendMessage("Incorrect!\n", event);
                App.triviaHandler.updateTriviaPoints(event.getAuthor().getId(), -1);
            }
            App.listening = false;
        }
    }


}
