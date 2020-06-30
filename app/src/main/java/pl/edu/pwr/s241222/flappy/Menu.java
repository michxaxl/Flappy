package pl.edu.pwr.s241222.flappy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity  {

    private static final String PREFERENCES_NAME = "Stats";

    private Button startBtn, resetBtn;
    private TextView lastScoreText, bestScoreText;
    private SharedPreferences preferences;
    private int pointsFromPreferences, bestPointsFromPreferences;
    private int lastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

        startBtn = findViewById(R.id.startButton);
        resetBtn = findViewById(R.id.resetButton);
        lastScoreText = findViewById(R.id.lastScoreText);
        bestScoreText = findViewById(R.id.bestScoreText);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPoints();
            }
        });

    }

    public void restoreData() {
        pointsFromPreferences = preferences.getInt("points", 0);
        bestPointsFromPreferences = preferences.getInt("bestPoints", 0);

        lastScoreText.setText("Ostatni Wynik: " + String.valueOf(pointsFromPreferences));
        bestScoreText.setText("Najlepszy Wynik: " + String.valueOf(bestPointsFromPreferences));
    }

    public void resetPoints() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt("points", 0);
        preferencesEditor.putInt("bestPoints", 0);
        preferencesEditor.commit();
        restoreData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        restoreData();
    }

}
