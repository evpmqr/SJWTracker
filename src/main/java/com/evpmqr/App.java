package com.evpmqr;

import com.evpmqr.actions.*;
import com.evpmqr.handlers.DataHandler;
import com.evpmqr.handlers.TriviaHandler;
import com.evpmqr.listener.BaseListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    public static boolean listening = false;
    public static TriviaHandler triviaHandler;
    public static DataHandler dataHandler;
    public static void main(String[] args) {
        dataHandler = new DataHandler();
        triviaHandler = new TriviaHandler(dataHandler);

        if (!dataHandler.loadData()) {
            dataHandler.init();
        }

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            dataHandler.resetPoints();
            dataHandler.saveData();
        }, 0, 8, TimeUnit.HOURS);
        try {
            BaseListener baseListener = new BaseListener();
            baseListener.addAction(new HelpAction("!help"));
            baseListener.addAction(new LeaderboardAction("!leaderboard"));
            baseListener.addAction(new SJWVoteAction("!vote"));
            baseListener.addAction(new QueueTriviaAction("!trivia"));
            baseListener.addAction(new AnswerTriviaAction("!answer"));
            baseListener.addAction(new TriviaLeaderboardAction("!tleaderboard"));
            baseListener.addAction(new CoinFlipAction("!coinflip"));
            baseListener.addAction(new RollAction("!roll"));
            JDA jda = new JDABuilder(AccountType.BOT).setToken(System.getProperty("token")).buildBlocking();
            jda.addEventListener(baseListener);

        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
