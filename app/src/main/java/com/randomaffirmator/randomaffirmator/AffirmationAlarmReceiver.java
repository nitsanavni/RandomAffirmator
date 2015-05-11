package com.randomaffirmator.randomaffirmator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AffirmationAlarmReceiver extends BroadcastReceiver {
    public AffirmationAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // sound the affirmation
        context.startService(new Intent(context, AffirmationPlayerService.class));
        // schedule the next one
        AffirmationScheduler.set(context);
    }
}
