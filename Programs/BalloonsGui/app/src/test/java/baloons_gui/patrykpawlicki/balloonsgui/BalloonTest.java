package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.graphics.Color;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Patryk Pawlicki on 23/03/2018.
 */

public class BalloonTest {
    public Context context;
    BalloonManager bm = new BalloonManager(context);

    @Test
    public void generateOneBalloon() throws Exception {
        bm.generateBalloon(context, 100, 100, 10, 10);
        assertEquals(bm.getBalloonsListSize(), 1);
    }
}
