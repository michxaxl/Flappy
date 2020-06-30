package pl.edu.pwr.s241222.flappy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "Stats";
    private SharedPreferences preferences;

    private GameObject game;
    private TextView pointsView;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = findViewById(R.id.gameObject);
        pointsView = findViewById(R.id.Points);
        points = 0;


        Handler myHandler = new Handler() {
            @Override
            public void handleMessage (Message msg) {
                savePoints(msg.what); // Odebranie punktow z GameObject i zapisanie do Preferences
                Intent intent = new Intent(MainActivity.this, Menu.class);
                startActivity(intent);
            }
        };
        game.setHandler(myHandler);

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

        pointsView.setText("0");
        game.setPointsView(pointsView);


    }

    /* Zapisanie punktow do Preferences */
    public void savePoints(int points) {
        int lastPoints = preferences.getInt("points", 0);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt("points", points);
        if(points > lastPoints) preferencesEditor.putInt("bestPoints", points);
        System.out.println("points: "+points+" lastPoints: "+lastPoints);
        preferencesEditor.commit();
    }
}
