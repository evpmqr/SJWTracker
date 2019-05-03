package com.evpmqr.objects;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.Collections;
import java.util.List;

public class TriviaResult {

    private String category;
    private String type;
    private String difficulty;
    private String question; // Needs Encoding
    private String correct_answer;
    private List<String> incorrect_answers;

    public TriviaResult() {
    }

    public void decode() {
        category = StringEscapeUtils.unescapeHtml(category);
        type = StringEscapeUtils.unescapeHtml(type);
        difficulty = StringEscapeUtils.unescapeHtml(difficulty);
        question = StringEscapeUtils.unescapeHtml(question);
        correct_answer = StringEscapeUtils.unescapeHtml(correct_answer);

        for (int i = 0; i < incorrect_answers.size(); i++) {
            incorrect_answers.set(i, StringEscapeUtils.unescapeHtml(incorrect_answers.get(i)));
        }
    }

    public String formatQuestion() {
        incorrect_answers.add(correct_answer);
        Collections.shuffle(incorrect_answers);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Category: ").append(category).append("\n");
        stringBuilder.append("Question: ").append(question).append("\n");
        for (int i = 0; i < incorrect_answers.size(); i++) {
            stringBuilder.append(i + 1).append(". ").append(incorrect_answers.get(i)).append("\n");
        }

        return stringBuilder.toString();
    }

    public String getAnswer(int idx) {
        return incorrect_answers.get(idx);
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

}
