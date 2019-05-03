package com.evpmqr;

import com.evpmqr.actions.*;
import com.evpmqr.data.DataHandler;
import com.evpmqr.listener.BaseListener;
import com.evpmqr.objects.Trivia;
import com.evpmqr.objects.TriviaResult;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class App {

    public static boolean listening = false;
    public static Trivia trivia = null;
    public static TriviaResult triviaResult = null;

    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler();


        if (!dataHandler.loadData()) {
            dataHandler.init();
        }
        try {
            BaseListener baseListener = new BaseListener();
            baseListener.addAction(new HelpAction("!help"));
            baseListener.addAction(new LeaderboardAction("!leaderboard", dataHandler));
            baseListener.addAction(new SJWVoteAction("!vote", dataHandler));
            baseListener.addAction(new QueueTriviaAction("!trivia"));
            baseListener.addAction(new AnswerTriviaAction("!answer"));
            JDA jda = new JDABuilder(AccountType.BOT).setToken(System.getProperty("token")).buildBlocking();
            jda.addEventListener(baseListener);

        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
