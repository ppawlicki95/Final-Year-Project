package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class highscores extends AppCompatActivity {

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

        editor.putInt("highscore1", highscore1);
        editor.putInt("highscore2", highscore2);
        editor.putInt("highscore3", highscore3);
        editor.putString("time1", newtime1);
        editor.putString("time2", newtime2);
        editor.putString("time3", newtime3);
        editor.apply();

        score1.setText(String.valueOf(preferences.getInt("highscore1", 0)));
        score2.setText(String.valueOf(preferences.getInt("highscore2", 0)));
        score3.setText(String.valueOf(preferences.getInt("highscore3", 0)));
        time1.setText(String.valueOf(preferences.getString("time1", "0:00")));
        time2.setText(String.valueOf(preferences.getString("time2", "0:00")));
        time3.setText(String.valueOf(preferences.getString("time3", "0:00")));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav_main = new Intent(highscores.this, MainActivity.class);
                highscores.this.finish();
                startActivity(nav_main);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                score1.setText(String.valueOf(preferences.getInt("highscore1", 0)));
                score2.setText(String.valueOf(preferences.getInt("highscore2", 0)));
                score3.setText(String.valueOf(preferences.getInt("highscore3", 0)));
                time1.setText(String.valueOf(preferences.getString("time1", "0:00")));
                time2.setText(String.valueOf(preferences.getString("time2", "0:00")));
                time3.setText(String.valueOf(preferences.getString("time3", "0:00")));
                //setContentView(R.layout.activity_highscores);
            }
        });
    }
}
