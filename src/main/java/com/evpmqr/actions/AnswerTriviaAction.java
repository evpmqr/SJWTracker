package com.evpmqr.actions;

import com.evpmqr.App;
import com.evpmqr.objects.TriviaResult;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AnswerTriviaAction extends Action {

    public AnswerTriviaAction(String command) {
        super(command);

    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (App.listening) {
            validate(message, App.triviaResult, event);
            App.listening = false;
        }
    }

    private void validate(String message, TriviaResult triviaResult, GuildMessageReceivedEvent event) {
        try {
            String[] answer = message.split("\\s+");
            int idx = Integer.parseInt(answer[1]) - 1;
            if (triviaResult.getAnswer(idx).equals(triviaResult.getCorrectAnswer())) {
                sendMessage(event.getAuthor().getName() + " is Correct!", event);
            } else {
                sendMessage("Incorrect!", event);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sendMessage("Incorrect number!", event);
        }
    }
}
