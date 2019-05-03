package com.evpmqr.objects;

import java.io.Serializable;
import java.util.Arrays;

public class User implements Serializable {

    private String name;
    private String id;
    private int sjwPoints;
    private int triviaPoints;
    private int votesLeft;
    private String[] alias;
    private boolean isAdmin;

    public User(String name, String id, boolean isAdmin, String...alias) {
        this.name = name;
        this.id = id;
        votesLeft = 2;
        this.alias = alias;
        this.isAdmin = isAdmin;
    }

    public boolean isName(String name) {
        return this.name.equalsIgnoreCase(name) || this.id.equalsIgnoreCase(name);
    }

    private boolean aliasContains(String name) {
        return Arrays.asList(alias).contains(name);
    }

    public boolean canVote() {
        return votesLeft > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSjwPoints() {
        return sjwPoints;
    }

    public void setSjwPoints(int sjwPoints) {
        this.sjwPoints = sjwPoints;
    }

    public int getTriviaPoints() {
        return triviaPoints;
    }

    public void setTriviaPoints(int triviaPoints) {
        this.triviaPoints = triviaPoints;
    }

    public int getVotesLeft() {
        return votesLeft;
    }

    public void setVotesLeft(int votesLeft) {
        this.votesLeft = votesLeft;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
