package com.randomaffirmator.randomaffirmator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AffirmationAlarmReceiver extends BroadcastReceiver {
    public AffirmationAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null &&
                intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // device boot - set the alarm for the next affirmation if activated
            SharedPreferences prefs = context
                    .getSharedPreferences(Keys.SHARED_PREFS, Context.MODE_PRIVATE);
            if (prefs.getBoolean(Keys.KEY_ACTIVE, false)) {
                AffirmationScheduler.set(context);
            }
        } else {
            // sound the affirmation
            context.startService(new Intent(context, AffirmationPlayerService.class));
            // schedule the next one
            AffirmationScheduler.set(context);
        }
    }
}
