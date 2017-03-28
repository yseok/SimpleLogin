package com.yuseok.android.simplelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class InappActivity extends AppCompatActivity {

    TextView textId, textPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp);

        textId = (TextView) findViewById(R.id.textId);
        textPw = (TextView) findViewById(R.id.textPw);

        Intent intent = getIntent();
        Log.i("getIntent ====", intent.getExtras().getString("id"));
        String id = intent.getExtras().getString("id");
        String pw = intent.getExtras().getString("pw");

        textId.setText(id);
        textId.setText(pw);


    }
}
