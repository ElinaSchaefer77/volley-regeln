package com.brethauerit.volleyball.data;

import android.content.res.Resources;

import com.brethauerit.volleyball.types.QuestionType;
import com.brethauerit.volleyball.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DQuestionReader {

    public static ArrayList<Question> questions = new ArrayList<>();

    public static ArrayList<Question> createDQuestions(Resources resources){
        if(questions.size()==0) {
            loadQuestions(resources);
            initializeAnswers(resources);
        }
        return questions;
    }


    private static void loadQuestions(Resources resources) {
        String allData = readTxt(resources, R.raw.fragen);
        String[] allDataSplitted = allData.split("\n");
        Question question;
        for (int i = 0; i < allDataSplitted.length; i++) {
            String[] words = allDataSplitted[i].split(" ");
            if (words[0].matches("[0-9]+")) {
                StringBuilder builder = new StringBuilder();
                for (int j = 1; j < words.length; j++) {
                    builder.append(words[j]);
                    builder.append(" ");
                }
                question = new Question(builder.toString(), QuestionType.DSchein, Integer.parseInt(words[0]));
                boolean allAnswersFound = false;
                int counter = 1;
                while (!allAnswersFound) {
                    if(counter+i<=allDataSplitted.length-1) {
                        String[] answerWords = allDataSplitted[i + counter].split(" ");
                        if (!answerWords[0].matches("[0-9]+")) {
                            StringBuilder builder1 = new StringBuilder();
                            for (int j = 1; j < answerWords.length; j++) {
                                builder1.append(answerWords[j]);
                                builder1.append(" ");
                            }
                            question.addAnswer(new Answer(builder1.toString(), answerWords[0]));
                            counter++;
                        } else {
                            allAnswersFound = true;
                        }
                    } else {
                        allAnswersFound = true;
                    }
                }

                questions.add(question);
                i = i + counter-1;
            }
        }
    }

    private static String readTxt(Resources resources, int id) {
        InputStream is = resources.openRawResource(id);
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(is, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        // Read everything into a StringBuilder
        try {
            stringBuilder.append(bufferedReader.readLine());
            while (bufferedReader.ready()) {
                stringBuilder.append("\r\n");
                stringBuilder.append(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Print the content of the file on the screen
        return stringBuilder.toString();
    }

    private static void initializeAnswers(Resources resources) {
        String allData = readTxt(resources, R.raw.antworten);
        String[] splitted = allData.split(" ");
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < splitted.length; i++) {
            if (!splitted[i].equals("")) {
                data.add(splitted[i]);
            }
        }
        for (int i = 0; i < data.size(); i = i + 2) {
            int question = Integer.parseInt(data.get(i)) - 1;
            Question quest = questions.get(question);
            String value = data.get(i + 1);
            ArrayList<Answer> answers = quest.getAnswers();
            for (int j = 0; j < answers.size(); j++) {
                if (value.equals(answers.get(j).getLetter())) {
                    answers.get(j).setTrue(true);
                }
            }
        }
    }

}
