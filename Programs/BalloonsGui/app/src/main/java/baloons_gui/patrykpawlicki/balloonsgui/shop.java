package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //"Back to main menu" button listener and onClick method
        ImageView back_btn = (ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nav_main = new Intent(shop.this, MainActivity.class);
                shop.this.finish();
                startActivity(nav_main);
            }
        });
    }

}
