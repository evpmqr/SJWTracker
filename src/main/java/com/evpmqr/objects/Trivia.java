package com.evpmqr.objects;

import java.util.List;
import java.util.Random;

public class Trivia {
    private int responseCode;
    private List<TriviaResult> results;

    public Trivia() {
    }

    public TriviaResult getRandom() {
        int idx = new Random().nextInt(results.size());
        TriviaResult result = results.get(idx);
        results.remove(idx);
        return result;
    }

    public void decodeResults() {
        results.forEach(TriviaResult::decode);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<TriviaResult> getTriviaResults() {
        return results;
    }

    public void setTriviaResults(List<TriviaResult> triviaResults) {
        this.results = triviaResults;
    }

    public static void main(String[] args) {

    }
}
