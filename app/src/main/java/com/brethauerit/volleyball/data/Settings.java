package com.brethauerit.volleyball.data;

import com.brethauerit.volleyball.types.QuestionClass;

public class Settings {

    private QuestionClass questionClass = QuestionClass.CUNDDSCHEIN;
    private boolean random = false;

    public void setQuestionClass(QuestionClass questionClass) {
        this.questionClass = questionClass;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public QuestionClass getQuestionClass() {
        return questionClass;
    }

    public boolean isRandom() {
        return random;
    }

    @Override
    public String toString() {
        return "Settings: Random: "+random +" Class "+ questionClass;
    }
}
