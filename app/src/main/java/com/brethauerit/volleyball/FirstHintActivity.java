package com.brethauerit.volleyball;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstHintActivity extends Activity {

    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_hint);
        okButton = (Button) findViewById(R.id.okbuttonhint);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstHintActivity.this.finish();
            }
        });
    }
}
