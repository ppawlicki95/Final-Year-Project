package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class responsible for displaying and updating the Highscores
 */
public class Highscores extends AppCompatActivity {

    public static int highscore1, highscore2, highscore3;
    public static String newtime1, newtime2, newtime3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        ImageView back_btn = (ImageView) findViewById(R.id.back_btn);
        ImageView reset_btn = (ImageView) findViewById(R.id.reset_btn);

        final TextView score1 = (TextView) findViewById(R.id.score1);
        final TextView score2 = (TextView) findViewById(R.id.score2);
        final TextView score3 = (TextView) findViewById(R.id.score3);

        final TextView time1 = (TextView) findViewById(R.id.time1);
        final TextView time2 = (TextView) findViewById(R.id.time2);
        final TextView time3 = (TextView) findViewById(R.id.time3);

        final SharedPreferences preferences = this.getSharedPreferences("Scores", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        if(highscore1 == 0) { //Import values from preferences if empty
            highscore1 = preferences.getInt("highscore1", 0);
            highscore2 = preferences.getInt("highscore2", 0);
            highscore3 = preferences.getInt("highscore3", 0);

            newtime1 = preferences.getString("time1", "0:00");
            newtime2 = preferences.getString("time2", "0:00");
            newtime3 = preferences.getString("time3", "0:00");
        }

        // Update the highscores and time in preferences
        editor.putInt("highscore1", highscore1);
        editor.putInt("highscore2", highscore2);
        editor.putInt("highscore3", highscore3);
        editor.putString("time1", newtime1);
        editor.putString("time2", newtime2);
        editor.putString("time3", newtime3);
        editor.apply();

        // Update the highscores and time in the UI
        score1.setText(String.valueOf(preferences.getInt("highscore1", 0)));
        score2.setText(String.valueOf(preferences.getInt("highscore2", 0)));
        score3.setText(String.valueOf(preferences.getInt("highscore3", 0)));
        time1.setText(String.valueOf(preferences.getString("time1", "0:00")));
        time2.setText(String.valueOf(preferences.getString("time2", "0:00")));
        time3.setText(String.valueOf(preferences.getString("time3", "0:00")));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav_main = new Intent(Highscores.this, MainActivity.class);
                Highscores.this.finish();
                startActivity(nav_main);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Reset button listener
                editor.clear();              // clears the preferences and assigns default values
                editor.commit();
                score1.setText(String.valueOf(preferences.getInt("highscore1", 0)));
                score2.setText(String.valueOf(preferences.getInt("highscore2", 0)));
                score3.setText(String.valueOf(preferences.getInt("highscore3", 0)));
                time1.setText(String.valueOf(preferences.getString("time1", "0:00")));
                time2.setText(String.valueOf(preferences.getString("time2", "0:00")));
                time3.setText(String.valueOf(preferences.getString("time3", "0:00")));
            }
        });
    }
}
