package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Screen metrics
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MainThread.SCREEN_WIDTH = dm.widthPixels;
        MainThread.SCREEN_HEIGHT = dm.heightPixels;

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Exit button listener and onClick method
        ImageView exit_btn = (ImageView)findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        //Shop button listener and onClick method
        ImageView shop_btn = (ImageView)findViewById(R.id.shop_btn);
        shop_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nav_shop = new Intent(MainActivity.this, shop.class);
                startActivity(nav_shop);
            }
        });

        //Start button listener and onClick method
        ImageView start_btn = (ImageView)findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nav_game = new Intent(MainActivity.this, game.class);
                startActivity(nav_game);
            }
        });
    }
}