package com.brethauerit.volleyball.data;

import com.brethauerit.volleyball.types.QuestionState;
import com.brethauerit.volleyball.types.QuestionType;

import java.util.ArrayList;

public class Question {

    private String text;
    private QuestionState state = QuestionState.UNANSWERED;
    private QuestionType type;
    private ArrayList<Answer> answers = new ArrayList<>();
    private int number;

    public Question(String text, QuestionType type, int number) {
        this.text = text;
        this.type = type;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public QuestionType getType() {
        return type;
    }

    public QuestionState getState() {
        return state;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer){
        this.answers.add(answer);
    }

    public String getText() {
        return text;
    }

    private void setState(QuestionState state){
        this.state = state;
    }
}
