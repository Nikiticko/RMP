package com.example.rmp_coursach;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("LifeCycle", "MainActivity: onCreate");

        EditText questionInput = findViewById(R.id.question_input);
        Button submitButton = findViewById(R.id.submit_button);
        Button openSiteButton = findViewById(R.id.open_site_button);
        TextView answerTextView = findViewById(R.id.answer_text_view);

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString("saved_question");
            if (savedText != null) {
                questionInput.setText(savedText);
            }
        }

        submitButton.setOnClickListener(v -> {
            String questionText = questionInput.getText().toString();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("question_text", questionText);
            startActivityForResult(intent, 1);
        });

        openSiteButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://e.kipu-rc.ru"));
            startActivity(browserIntent);
        });
    }

    // Обработка возвращённого результата
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra("result_key");
            TextView answerTextView = findViewById(R.id.answer_text_view);
            answerTextView.setText(result);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle", "MainActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "MainActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "MainActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle", "MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle", "MainActivity: onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText questionInput = findViewById(R.id.question_input);
        String currentText = questionInput.getText().toString();
        outState.putString("saved_question", currentText);
        Log.d("LifeCycle", "MainActivity: onSaveInstanceState");
    }
}
