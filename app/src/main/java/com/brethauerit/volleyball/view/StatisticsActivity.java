package com.brethauerit.volleyball.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.brethauerit.volleyball.R;
import com.brethauerit.volleyball.data.Statistic;
import com.brethauerit.volleyball.helper.Storage;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticsActivity extends AppCompatActivity {

    private TextView all, alld, allc, right, rightd, rightc, wrong, wrongd, wrongc, dlist, clist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        if(getActionBar()!=null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Statistic statistic = Storage.loadStatistics(this);
        if(statistic==null){
            statistic = new Statistic();
        }
        all = (TextView) findViewById(R.id.number_answered);
        alld = (TextView) findViewById(R.id.number_answered_d);
        allc= (TextView) findViewById(R.id.number_answered_c);
        right = (TextView) findViewById(R.id.rightall);
        rightd = (TextView) findViewById(R.id.right_d);
        rightc = (TextView) findViewById(R.id.right_c);
        wrong = (TextView) findViewById(R.id.wrong);
        wrongd = (TextView) findViewById(R.id.wrong_d);
        wrongc = (TextView) findViewById(R.id.wrong_c);
        dlist = (TextView) findViewById(R.id.wrong_d_list);
        clist = (TextView) findViewById(R.id.wrong_c_list);

        all.setText(statistic.getAnsweredQuestions()+"");
        alld.setText(statistic.getAnsweredDQuestions()+"");
        allc.setText(statistic.getAnsweredCQuestions()+"");
        right.setText(statistic.getRightQuestions()+"");
        rightd.setText(statistic.getRightDQuestions()+"");
        rightc.setText(statistic.getRightCQuestions()+"");
        wrong.setText(statistic.getWrongQuestions()+"");
        wrongd.setText(statistic.getWrongDQuestions()+"");
        wrongc.setText(statistic.getWrongCQuestions()+"");
        ArrayList<Integer> dquests = statistic.getAllWrongAnsweredDQuestions();
        Collections.sort(dquests);
        ArrayList<Integer> cquests = statistic.getAllWrongsAnsweredCQuestions();
        Collections.sort(cquests);

        dlist.setText(dquests.toString()+"");
        clist.setText(cquests.toString()+"");



    }
}
