package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    Random rd = new Random();
    String game_mode = "playWithBot";
    int[][] virtualM = new int[3][3];
    ImageView[][] images = new ImageView[3][3];
    Button changeMode;
    boolean player = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Play Game");
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        changeMode = (Button) findViewById(R.id.btnChangeMode);
        int k = 1;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                images[i][j] = (ImageView) findViewById
                        (getResources().getIdentifier
                                ("img" + k, "id", getPackageName()));
                k++;
            }

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                images[i][j].setOnClickListener(this);
    }

    private void playGame(View v) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (images[i][j].getId() == v.getId()) {
                    if (virtualM[i][j] == 0) {
                        if (player) {
                            images[i][j].setImageResource(R.drawable.red_x);
                            virtualM[i][j] = 1;
                            if (game_mode.equals("multiPlayer"))
                                player = false;
                            else {
                                if (gameOver() == 0) {
                                    computerPlay();
                                }
                            }
                        } else {
                            images[i][j].setImageResource(R.drawable.red_0);
                            virtualM[i][j] = -1;
                            player = true;
                        }
                    }
                }
            }
    }

    private void computerPlay() {
        int a = -1, b = -1;
        do {
            a = rd.nextInt(3);
            b = rd.nextInt(3);
        } while (virtualM[a][b] != 0);

        images[a][b].setImageResource(R.drawable.red_0);
        virtualM[a][b] = -1;
    }

    private int gameOver() {
        boolean test = false;
        int sl, sc, sd1 = 0, sd2 = 0;

        for (int i = 0; i < 3; i++) {
            sl = 0;
            sc = 0;
            for (int j = 0; j < 3; j++) {
                sl += virtualM[i][j];
                sc += virtualM[j][i];
                if (i == j)
                    sd1 += virtualM[i][j];
                if (i + j == 2)
                    sd2 += virtualM[i][j];

                if (virtualM[i][j] == 0)
                    test = true;
            }
            if (sl == 3 || sc == 3)
                return 1;
            if (sl == -3 || sc == -3)
                return 2;
        }
        if (sd1 == 3 || sd2 == 3)
            return 1;
        if (sd1 == -3 || sd2 == -3)
            return 2;
        if (!test)
            return 3;

        return 0;
    }

    @Override
    public void onClick(View v) {
        playGame(v);
        if (gameOver() == 1)
            Toast.makeText(this, "Player 1 Win", Toast.LENGTH_LONG).show();
        else if (gameOver() == 2)
            Toast.makeText(this, "Player 2 Win", Toast.LENGTH_LONG).show();
        else if (gameOver() == 3)
            Toast.makeText(this, "Draw", Toast.LENGTH_LONG).show();

        if (gameOver() != 0) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    images[i][j].setOnClickListener(null);
                }
        }
    }

    public void restart(View v) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                images[i][j].setImageResource(R.drawable.shapes);
                virtualM[i][j] = 0;
            }
        player = true;

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                images[i][j].setOnClickListener(this);
            }

        if (game_mode == "playWithBot") {
            game_mode = "playWithBot";
        }else{
            game_mode = "multiPlayer";
        }
    }

    public void changeMode(View view) {
        restart(view);
        if (game_mode == "playWithBot") {
            game_mode = "multiPlayer";
            changeMode = (Button) findViewById(R.id.btnChangeMode);
            changeMode.setText("Play With Bot");
        } else if(game_mode == "multiPlayer") {
            game_mode = "playWithBot";
            changeMode = (Button) findViewById(R.id.btnChangeMode);
            changeMode.setText("MultiPlayer");
        }else{
            game_mode = "playWithBot";
            changeMode = (Button) findViewById(R.id.btnChangeMode);
        }
    }
}