package com.evpmqr;

import com.evpmqr.data.DataHandler;
import com.evpmqr.listener.SJWListener;
import com.evpmqr.listener.TriviaListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class App {

    private static JDA jda;

    public static Guild getGuild() {
        return jda.getGuildById("236334952927461378");
    }

    public static void main(String[] args) {
        DataHandler dataHandler = new DataHandler();
        if (!dataHandler.loadData()) {
            dataHandler.init();
        }
        try {
            jda = new JDABuilder(AccountType.BOT).setToken("NDc0MzA2OTg0NTY5NDcwOTc2.XMsFtA.C02CkE2_23LNff5w2VaH8jQrfh4").buildBlocking();
            jda.addEventListener(new TriviaListener(), new SJWListener(dataHandler));

        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
