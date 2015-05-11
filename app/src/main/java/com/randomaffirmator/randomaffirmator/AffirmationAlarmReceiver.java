package com.randomaffirmator.randomaffirmator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AffirmationAlarmReceiver extends BroadcastReceiver {
    public AffirmationAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
