package com.evpmqr.actions;

import com.evpmqr.App;
import com.evpmqr.objects.Trivia;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class QueueTriviaAction extends Action {

    public QueueTriviaAction(String command) {
        super(command);
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (!App.listening) {
            App.listening = true;
            if (App.trivia == null || App.trivia.getTriviaResults().isEmpty()) {
                getQuestions();
            }
            App.triviaResult = App.trivia.getRandom();
            sendMessage(App.triviaResult.formatQuestion(), event);
        }
    }

    private void getQuestions() {
        try {
            HttpRequestFactory requestFactory
                    = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl("https://opentdb.com/api.php?amount=10&difficulty=hard"));
            String rawResponse = request.execute().parseAsString();
            Gson g = new Gson();
            App.trivia = g.fromJson(rawResponse, Trivia.class);
            App.trivia.decodeResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
