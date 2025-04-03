package com.example.rmp_coursach;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;


public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("LifeCycle", "SecondActivity: onCreate");

        String question = getIntent().getStringExtra("question_text");

        TextView textView = findViewById(R.id.question_text_view);
        textView.setText(question);
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", "SecondActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "SecondActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "SecondActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", "SecondActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "SecondActivity: onDestroy");
    }
}
