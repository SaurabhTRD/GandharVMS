package com.android.gandharvms;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", getClass().getSimpleName() + " - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", getClass().getSimpleName() + " - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", getClass().getSimpleName() + " - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", getClass().getSimpleName() + " - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", getClass().getSimpleName() + " - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", getClass().getSimpleName() + " - onDestroy");
    }
}
