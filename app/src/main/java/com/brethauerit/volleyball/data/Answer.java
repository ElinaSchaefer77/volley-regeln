package com.brethauerit.volleyball.data;

public class Answer {

    private boolean isTrue = false;
    private String text;
    private String letter;

    public Answer(String text, String letter) {
        this.text = text;
        this.letter = letter;
    }

    public String getText() {
        return text;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getLetter() {
        return letter;
    }
}
