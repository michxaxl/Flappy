package pl.edu.pwr.s241222.flappy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GameObject game;
    private TextView pointsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = findViewById(R.id.gameObject);
        pointsView = findViewById(R.id.Points);

        pointsView.setText("0");
        game.setPointsView(pointsView);

        if(game.isDead()){
            System.out.println("DEAD");
        }



//        pointsView.setText("Hello");
//        pointsView.setText(String.valueOf(game.getPoints()));
//        System.out.println(game.getPoints());
    }
}
