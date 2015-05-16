package com.randomaffirmator.randomaffirmator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SettingsActivityFragment extends Fragment {

    private boolean mActive;
    private Handler mHandler;
    private java.lang.Runnable mRunnable;

    public SettingsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(Keys.SHARED_PREFS, Context.MODE_PRIVATE);
        mActive = prefs.getBoolean(Keys.KEY_ACTIVE, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(Keys.SHARED_PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(Keys.KEY_ACTIVE, mActive).apply();
        if (null != mHandler && null != mRunnable)
            mHandler.removeCallbacks(mRunnable);
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
        if (mActive) {
            AffirmationScheduler.set(context);
        } else {
            AffirmationScheduler.unset(context);
        }
    }

    private void updateText(TextView button) {
        if (mActive)
            button.setText(getString(R.string.deactivate));
        else
            button.setText(getString(R.string.activate));
    }
}
