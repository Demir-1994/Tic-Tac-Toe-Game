package com.example.tictactoegame;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tic Tac Toe");
    }

    public void onAboutClick(View view) {
        Intent intent = new Intent(this, AboutGameActivity.class);
        startActivity(intent);
    }

    public void onPlayGameClick(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }
}