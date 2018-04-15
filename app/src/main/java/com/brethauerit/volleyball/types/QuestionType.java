package com.brethauerit.volleyball.types;

public enum QuestionType {
    CSchein ("C-Schein"),
    DSchein ("D-Schein");

    String name;

    QuestionType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
