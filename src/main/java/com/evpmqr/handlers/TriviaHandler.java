package com.evpmqr.handlers;

import com.evpmqr.objects.Trivia;
import com.evpmqr.objects.TriviaResult;
import com.evpmqr.objects.User;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;

public class TriviaHandler {
    private Trivia trivia;
    private TriviaResult triviaResult;
    private DataHandler dataHandler;

    public TriviaHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public void getQuestions() {
        try {
            HttpRequestFactory requestFactory
                    = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl("https://opentdb.com/api.php?amount=10&difficulty=hard"));
            String rawResponse = request.execute().parseAsString();
            Gson g = new Gson();
            trivia = g.fromJson(rawResponse, Trivia.class);
            trivia.decodeResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validate(String message, TriviaResult triviaResult, GuildMessageReceivedEvent event) {
        try {
            String[] answer = message.split("\\s+");
            int idx = Integer.parseInt(answer[1]) - 1;
            if (triviaResult.getAnswer(idx).equals(triviaResult.getCorrectAnswer())) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return false;
        }
    }

    public void setQuestion() {
        triviaResult = trivia.getRandom();
    }

    public void updateTriviaPoints(String id) {
        User user = dataHandler.fetchUser(id);
        user.setTriviaPoints(user.getTriviaPoints() + 1);
        dataHandler.saveData();
    }

    public Trivia getTrivia() {
        return trivia;
    }

    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
    }

    public TriviaResult getTriviaResult() {
        return triviaResult;
    }

    public void setTriviaResult(TriviaResult triviaResult) {
        this.triviaResult = triviaResult;
    }
}
