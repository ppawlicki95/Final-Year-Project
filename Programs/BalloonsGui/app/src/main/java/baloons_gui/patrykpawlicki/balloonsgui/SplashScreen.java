package baloons_gui.patrykpawlicki.balloonsgui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreen extends Activity {

    private final int splashScreenDuration = 500;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent nav_main = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(nav_main);
                SplashScreen.this.finish();
            }
        }, splashScreenDuration);
    }
}