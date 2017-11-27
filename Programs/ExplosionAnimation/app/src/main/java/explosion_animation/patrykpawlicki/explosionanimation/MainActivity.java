package explosion_animation.patrykpawlicki.explosionanimation;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button explosion_btn = (Button)findViewById(R.id.button);
        ImageView animation = (ImageView)findViewById(R.id.imageView);
        animation.setImageResource(R.drawable.explosion);
        final AnimationDrawable explosionAnimation = (AnimationDrawable)animation.getDrawable();

        explosion_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                explosionAnimation.stop();
                explosionAnimation.start();
            }
        });
    }
    }
