package com.randomaffirmator.randomaffirmator;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.Calendar;

public class AffirmationPlayerService extends Service {
    private MediaPlayer mediaPlayer;

    public AffirmationPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.here_and_now);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (checkTimeOfDay() && checkMute())
            mediaPlayer.start();
        return 1;
    }

    private boolean checkMute() {
        AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                return true;
            default:
            case AudioManager.RINGER_MODE_SILENT:
            case AudioManager.RINGER_MODE_VIBRATE:
                return false;
        }
    }

    private boolean checkTimeOfDay() {
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hourOfDay > 10 && hourOfDay < 20;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

}
