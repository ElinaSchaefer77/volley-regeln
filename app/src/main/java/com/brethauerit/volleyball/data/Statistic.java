package com.brethauerit.volleyball.data;

import com.brethauerit.volleyball.types.QuestionState;
import com.brethauerit.volleyball.types.QuestionType;

import java.util.ArrayList;

public class Statistic {

    private int answeredQuestions = 0;
    private int answeredDQuestions = 0;
    private int answeredCQuestions = 0;
    private int rightQuestions = 0;
    private int rightDQuestions = 0;
    private int rightCQuestions = 0;
    private int wrongQuestions = 0;
    private int wrongDQuestions = 0;
    private int wrongCQuestions = 0;

    private ArrayList<Integer> allWrongAnsweredDQuestions = new ArrayList<>();
    private ArrayList<Integer> allWrongsAnsweredCQuestions= new ArrayList<>();

    private Question lastQuestion = null;

    public ArrayList<Integer> getAllWrongAnsweredDQuestions() {
        return allWrongAnsweredDQuestions;
    }

    public int getAnsweredCQuestions() {
        return answeredCQuestions;
    }

    public int getAnsweredDQuestions() {
        return answeredDQuestions;
    }

    public int getAnsweredQuestions() {
        return answeredQuestions;
    }

    public ArrayList<Integer> getAllWrongsAnsweredCQuestions() {
        return allWrongsAnsweredCQuestions;
    }

    public int getRightCQuestions() {
        return rightCQuestions;
    }

    public int getRightDQuestions() {
        return rightDQuestions;
    }

    public int getRightQuestions() {
        return rightQuestions;
    }

    public int getWrongCQuestions() {
        return wrongCQuestions;
    }

    public int getWrongDQuestions() {
        return wrongDQuestions;
    }

    public int getWrongQuestions() {
        return wrongQuestions;
    }

    public Question getLastQuestion() {
        return lastQuestion;
    }

    public void onAnsweredQuestion(Question question, QuestionState state){
        lastQuestion = question;
        if(state!=QuestionState.UNANSWERED) {
            answeredQuestions++;
            if (question.getType().equals(QuestionType.DSchein)) {
                answeredDQuestions++;
            } else {
                answeredCQuestions++;
            }
            if (state.equals(QuestionState.RIGHT)) {
                rightQuestions++;
                if (question.getType().equals(QuestionType.DSchein)) {
                    if(allWrongAnsweredDQuestions.contains(question.getNumber())){
                        allWrongAnsweredDQuestions.remove(new Integer(question.getNumber()));
                    }
                    rightDQuestions++;
                } else {
                    if(allWrongsAnsweredCQuestions.contains(question.getNumber())){
                        allWrongsAnsweredCQuestions.remove(new Integer(question.getNumber()));
                    }
                    rightCQuestions++;
                }
            } else {
                wrongQuestions++;
                if (question.getType().equals(QuestionType.DSchein)) {
                    wrongDQuestions++;
                    if (!allWrongAnsweredDQuestions.contains(question.getNumber())) {
                        allWrongAnsweredDQuestions.add(question.getNumber());
                    }
                } else {
                    if (!allWrongsAnsweredCQuestions.contains(question.getNumber())) {
                        allWrongsAnsweredCQuestions.add(question.getNumber());
                    }
                    wrongCQuestions++;
                }
            }
        }
    }
}
