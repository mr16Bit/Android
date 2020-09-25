package com.example.firstexemple;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Testing_log", "onCreate method");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Testing_log", "onStart method");
        Log.i("Testing_log", "onStart method2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Testing_log", "onStop method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Testing_log", "onDestroy method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Testing_log", "onPause method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Testing_log", "onResume method");
    }
}