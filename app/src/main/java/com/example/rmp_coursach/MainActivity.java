package com.example.rmp_coursach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String savedLang = prefs.getString("lang", "en");
        String currentLang = Locale.getDefault().getLanguage();
        if (!currentLang.equals(savedLang)) {
            setLocale(savedLang);
        }

        int themeMode = prefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(themeMode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("LifeCycle", "MainActivity: onCreate");

        EditText questionInput = findViewById(R.id.question_input);
        Button submitButton = findViewById(R.id.submit_button);
        Button openSiteButton = findViewById(R.id.open_site_button);
        Button languageButton = findViewById(R.id.language_button);
        Button themeButton = findViewById(R.id.theme_button);
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
            intent.putExtra("poll_id", 42);
            intent.putExtra("is_anonymous", true);
            startActivityForResult(intent, 1);
        });

        openSiteButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(browserIntent);
        });

        languageButton.setOnClickListener(v -> {
            String currentLangCode = prefs.getString("lang", "en");
            String newLangCode = currentLangCode.equals("ru") ? "en" : "ru";
            prefs.edit().putString("lang", newLangCode).apply();
            if (!Locale.getDefault().getLanguage().equals(newLangCode)) {
                setLocale(newLangCode);
            }
        });

        themeButton.setOnClickListener(v -> {
            int currentMode = AppCompatDelegate.getDefaultNightMode();
            int newMode = (currentMode == AppCompatDelegate.MODE_NIGHT_YES)
                    ? AppCompatDelegate.MODE_NIGHT_NO
                    : AppCompatDelegate.MODE_NIGHT_YES;

            prefs.edit().putInt("theme_mode", newMode).apply();
            AppCompatDelegate.setDefaultNightMode(newMode);
            recreate();
        });
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
    }

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
