package com.brethauerit.volleyball.view;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brethauerit.volleyball.BuildConfig;
import com.brethauerit.volleyball.FirstHintActivity;
import com.brethauerit.volleyball.ImpressumActivity;
import com.brethauerit.volleyball.data.Answer;
import com.brethauerit.volleyball.data.CQuestionReader;
import com.brethauerit.volleyball.data.DQuestionReader;
import com.brethauerit.volleyball.data.Question;
import com.brethauerit.volleyball.types.QuestionClass;
import com.brethauerit.volleyball.types.QuestionState;
import com.brethauerit.volleyball.types.QuestionType;
import com.brethauerit.volleyball.R;
import com.brethauerit.volleyball.data.Settings;
import com.brethauerit.volleyball.data.Statistic;
import com.brethauerit.volleyball.helper.ActivityHelper;
import com.brethauerit.volleyball.helper.Storage;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RelativeLayout progress;
    private TextView question, questionclass, questionnumber;
    private ListView listView;
    private Button weiter, skip;
    private ArrayList<Question> questions = new ArrayList<>();
    private Settings settings;
    private Statistic statistic;
    private Random random = new Random();
    private ArrayAdapter<String> adapterCompiti;
    private SparseBooleanArray array;

    private int actual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
            Fabric.with(this, new Answers());
        }
        setContentView(R.layout.activity_main);
        progress = (RelativeLayout) findViewById(R.id.main_progress);
        question = (TextView) findViewById(R.id.question);
        listView = (ListView) findViewById(R.id.listView);
        questionclass = (TextView) findViewById(R.id.questionclass);
        questionnumber = (TextView) findViewById(R.id.question_number);
        skip = (Button) findViewById(R.id.skip_button);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setSelector(R.drawable.listview_bg);
        weiter = (Button) findViewById(R.id.button);
        weiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answered = weiter.getText().toString().equals(getResources().getString(R.string.weiter));
                weiter.setText(R.string.weiter);
                if (answered) {
                    getNextQuestion();
                    start();
                } else {
                    solve(false);
                }

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextQuestion();
                statistic.onAnsweredQuestion(questions.get(actual), QuestionState.UNANSWERED);
                Storage.storeStatistics(MainActivity.this, statistic);
            }
        });
        if(Storage.isFirstTimeLaunched(this)){
            ActivityHelper.openActivity(this, FirstHintActivity.class);
        }
    }

    private void getNextQuestion() {
        Log.d(TAG, settings.toString());
        if (settings.isRandom()) {
            actual = random.nextInt(questions.size() - 1);
        } else {
            actual++;
            if (actual >= questions.size()) {
                actual = 0;
            }
        }
        start();
            }

    @Override
    protected void onResume() {
        super.onResume();
        statistic = Storage.loadStatistics(this);
        if (statistic == null) {
            statistic = new Statistic();
            Storage.storeStatistics(this, statistic);
        }
        settings = Storage.loadSettings(this);
        if (settings == null) {
            settings = new Settings();
            Storage.storeSettings(this, settings);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                readQuestions();
                initializeActual();
                start();
            }
        });
    }

    private void readQuestions() {
        progress.setVisibility(View.VISIBLE);
        QuestionClass questionClass = settings.getQuestionClass();
        Log.d(TAG, "Anahl Fragen 1: " + questions.size());
        switch (questionClass) {
            case CSCHEIN:
                questions = new ArrayList<>(CQuestionReader.createCQuestions(getResources()));
                break;
            case DSCHEIN:
                questions =  new ArrayList<>(DQuestionReader.createDQuestions(getResources()));
                break;
            case CUNDDSCHEIN:
                questions =  new ArrayList<>(CQuestionReader.createCQuestions(getResources()));
                questions.addAll(DQuestionReader.createDQuestions(getResources()));
                break;
        }
        Log.d(TAG, "Anahl Fragen 2: " + questions.size());
        progress.setVisibility(View.GONE);
    }

    private void initializeActual() {
        Question question = statistic.getLastQuestion();
        if (question == null || !validateType(question.getType(), settings.getQuestionClass())) {
            actual = 0;
        } else {
            actual = 0;
            for (int i = 0; i < questions.size(); i++) {
                if (question.getText().equals(questions.get(i).getText())) {
                    actual = i;
                    break;
                }
            }
        }
    }

    private boolean validateType(QuestionType type, QuestionClass questionClass) {
        boolean isValidated = false;
        switch (type) {
            case CSchein:
                isValidated = questionClass == QuestionClass.CSCHEIN || questionClass == QuestionClass.CUNDDSCHEIN;
                break;
            case DSchein:
                isValidated = questionClass == QuestionClass.DSCHEIN || questionClass == QuestionClass.CUNDDSCHEIN;
                break;
        }
        return isValidated;
    }


    private void start() {
        weiter.setText(R.string.ok);
        Question quest = questions.get(actual);
        question.setText(quest.getText());
        questionnumber.setText(quest.getNumber()+"");
        questionclass.setText(quest.getType().getName());
        final ArrayList<String> list = new ArrayList<>();
        ArrayList<Answer> answers = quest.getAnswers();
        for (int i = 0; i < answers.size(); i++) {
            list.add(answers.get(i).getText());
        }

        adapterCompiti = new ArrayAdapter<String>(this, R.layout.answer_list_layout, list);
        listView.setSelector(R.drawable.listview_bg);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapterCompiti);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "ON SCROLL");
                boolean answered = weiter.getText().toString().equals(getResources().getString(R.string.weiter));
                if(answered){
                    Log.d(TAG, "SCROLLED AND SOLVE");
                    solve(true);
                }
            }
        });
    }

    private void solve(boolean scrolled) {
        if(!scrolled) {
            array = listView.getCheckedItemPositions();
        }
        List<Answer> answers = questions.get(actual).getAnswers();
        boolean answeredcorrect = true;
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).isTrue() != array.get(i)) {
                answeredcorrect = false;
            }
            int firstPosition = listView.getFirstVisiblePosition() - listView.getHeaderViewsCount();
            int wantedChild = i - firstPosition;
            if (wantedChild < 0 || wantedChild >= listView.getChildCount()) {
                Log.w(TAG, "Unable to get view for desired position, because it's not being displayed on screen.");
            } else {
                View wantedView = listView.getChildAt(wantedChild);
                if(answers.get(i).isTrue()){
                    if(array.get(i)){
                        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.right_right);
                        set.setTarget(wantedView);
                        set.start();
                        //IST RICHTIG UND RICHTIG ANGEKREUZT
                    } else {
                        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.right_false);
                        set.setTarget(wantedView);
                        set.start();
                        //IST RICHTIG ABER NICHT ANGEKREUZT
                    }
                } else {
                    if(array.get(i)){
                        //IST FALSCH ABER RICHTIG angezeugt
                        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.false_right);
                        set.setTarget(wantedView);
                        set.start();
                    }
                }
            }
        }
        if (answeredcorrect) {
            statistic.onAnsweredQuestion(questions.get(actual), QuestionState.RIGHT);

        } else {
            statistic.onAnsweredQuestion(questions.get(actual), QuestionState.WRONG);
        }

        Storage.storeStatistics(this, statistic);
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            ActivityHelper.openActivity(this, SettingsActivity.class);
        }
        if (id == R.id.statistics) {
            ActivityHelper.openActivity(this, StatisticsActivity.class);
        }
        if(id == R.id.impressum) {
            ActivityHelper.openActivity(this, ImpressumActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }
}
