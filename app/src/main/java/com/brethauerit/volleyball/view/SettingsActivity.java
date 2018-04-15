package com.brethauerit.volleyball.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.brethauerit.volleyball.types.QuestionClass;
import com.brethauerit.volleyball.R;
import com.brethauerit.volleyball.data.Settings;
import com.brethauerit.volleyball.helper.Storage;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private Settings settings;

    private RadioButton cAndD, c, d;
    private CheckBox random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(getActionBar()!=null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        settings = Storage.loadSettings(this);
        Log.d(TAG, settings.toString());
        if(settings==null){
            settings = new Settings();
        }
        random = (CheckBox) findViewById(R.id.random_sort);
        cAndD = (RadioButton) findViewById(R.id.cAndD);
        c = (RadioButton) findViewById(R.id.JustC);
        d = (RadioButton) findViewById(R.id.JustD);

        random.setChecked(settings.isRandom());

        QuestionClass questionClass = settings.getQuestionClass();
        switch (questionClass){
            case CUNDDSCHEIN:
                cAndD.setChecked(true);
                break;
            case CSCHEIN:
                c.setChecked(true);
                break;
            case DSCHEIN:
                d.setChecked(true);
                break;
        }


        random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setRandom(isChecked);
                Storage.storeSettings(SettingsActivity.this, settings);
            }
        });
        cAndD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    settings.setQuestionClass(QuestionClass.CUNDDSCHEIN);
                    Storage.storeSettings(SettingsActivity.this, settings);
                }
            }
        });
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    settings.setQuestionClass(QuestionClass.CSCHEIN);
                    Storage.storeSettings(SettingsActivity.this, settings);
                }
            }
        });
        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    settings.setQuestionClass(QuestionClass.DSCHEIN);
                    Storage.storeSettings(SettingsActivity.this, settings);
                }
            }
        });
    }
}
