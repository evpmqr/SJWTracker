package com.evpmqr.listener;

import com.evpmqr.objects.Trivia;
import com.evpmqr.objects.TriviaResult;
import com.google.gson.Gson;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TriviaListener extends BaseListener {
    private Trivia trivia;
    private boolean listening = false;
    private TriviaResult triviaResult;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if (!event.getAuthor().isBot()) {
            if (message.startsWith("init trivia")) {
                if (!listening) {
                    listening = true;
                    if (trivia == null || trivia.getTriviaResults().isEmpty()) {
                        getQuestions();
                    }
                    triviaResult = trivia.getRandom();
                    sendMessage(triviaResult.formatQuestion(), event);

                } else {
                    sendMessage("Trivia question already in queue!", event);
                }
            } else if (message.startsWith("a")) {

                if (listening) {
                    validate(message, triviaResult, event);
                    listening = false;
                }
            }
        }
    }

    private void validate(String message, TriviaResult triviaResult, GuildMessageReceivedEvent event) {
        try {
            String[] answer = message.split("\\s+");
            int idx = Integer.parseInt(answer[1]) - 1;
            if (idx >= triviaResult.getIncorrect_answers().size()) {
                sendMessage("Incorrect number!", event);
            }
            if (triviaResult.getAnswer(idx).equals(triviaResult.getCorrect_answer())) {
                sendMessage(event.getAuthor().getName() + " is Correct!", event);
            } else {
                sendMessage("Incorrect!", event);
            }
            listening = false;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sendMessage("Incorrect number!", event);
            listening = false;
        }
    }

    private void getQuestions() {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL("https://opentdb.com/api.php?amount=10&difficulty=hard");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            Gson g = new Gson();
            trivia = g.fromJson(response.toString(), Trivia.class);
            trivia.decodeResults();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
