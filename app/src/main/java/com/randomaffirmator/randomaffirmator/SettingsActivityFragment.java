package com.randomaffirmator.randomaffirmator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment {

    private static final String SHARED_PREFS = "RandomAffirmator.SharedPrefs";
    private static final String KEY_ACTIVE = "SettingsActivityFragment.KEY_ACTIVE";
    private boolean mActive;
    private Handler mHandler;
    private java.lang.Runnable mRunnable;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public SettingsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        mActive = prefs.getBoolean(KEY_ACTIVE, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_ACTIVE, mActive).apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView button = (TextView) ret.findViewById(R.id.activate_button);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActive = !mActive;
                updateText(button);
                setAlarm();
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 1000);
            }
        });
        updateText(button);
        return ret;
    }

    private void setAlarm() {
        Context context = getActivity();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (mActive) {
            if (null == alarmIntent) {
                Intent intent = new Intent(context, AffirmationAlarmReceiver.class);
                alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            }
            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            6 * 1000, alarmIntent);
        } else {
            if (null != alarmIntent)
                alarmMgr.cancel(alarmIntent);
        }
    }

    private void updateText(TextView button) {
        if (mActive)
            button.setText(getString(R.string.deactivate));
        else
            button.setText(getString(R.string.activate));
    }
}
