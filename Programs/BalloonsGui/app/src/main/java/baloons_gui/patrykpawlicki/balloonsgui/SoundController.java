package baloons_gui.patrykpawlicki.balloonsgui;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Patryk Pawlicki on 26/03/2018.
 */

/**
 * Class responsible for the sounds used by the game
 */
public class SoundController {

    SoundPool snd;
    int pop_sound, beep_sound, rainbow_sound;

    /**
     * SoundController constructor. Sets up a new SoundPool to be used by the in game sound effects.
     * @param context - interface which allows access to resources and states
     */
    public SoundController(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            snd = new android.media.SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
            pop_sound = snd.load(context, R.raw.pop_sound, 1);
            beep_sound = snd.load(context, R.raw.beep_sound, 1);
            rainbow_sound = snd.load(context, R.raw.rainbow_sound, 1);
        } else {
            snd = new android.media.SoundPool(10, AudioManager.STREAM_MUSIC, 1);
            pop_sound = snd.load(context, R.raw.pop_sound, 1);
            beep_sound = snd.load(context, R.raw.beep_sound, 1);
            rainbow_sound = snd.load(context, R.raw.rainbow_sound, 1);
        }
    }
}
