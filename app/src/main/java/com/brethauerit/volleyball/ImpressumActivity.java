package com.brethauerit.volleyball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImpressumActivity extends AppCompatActivity {

    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_hint);
        if(getActionBar()!=null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        okButton = (Button) findViewById(R.id.okbuttonhint);
        okButton.setVisibility(View.GONE);
    }
}
