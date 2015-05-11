package com.randomaffirmator.randomaffirmator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by nitsa_000 on 11-May-15.
 */
public abstract class AffirmationScheduler {

    public static void set(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AffirmationAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        // between 30 and 90 minutes
        Random r = new Random();
        int nextAlarmTimeMinutes = r.nextInt(90 - 30) + 30;
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                TimeUnit.MINUTES.toMillis(nextAlarmTimeMinutes), alarmIntent);
    }

    public static void unset(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AffirmationAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.cancel(alarmIntent);
    }
}
