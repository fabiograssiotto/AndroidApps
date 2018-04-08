package com.fabiogr.simpletimer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SimpleTimerActivity extends Activity {

    private TextView mTimerText;
    private String KEY_TIMER = "timer";
    private int mTimerData = 0;
    private AsyncTimer mTimerTask = null;

    private void saveTimerData() {
        // Save timer data
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(KEY_TIMER, mTimerData );
        edit.commit();
    }

    // For the Async Task to update the timer data.
    public void updateTimerData(int data) {
        mTimerData = data;
        mTimerText.setText(String.valueOf(mTimerData));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_timer);

        // Set up the UI
        mTimerText = (TextView) findViewById(R.id.timerText);
        Button startButton = (Button) findViewById(R.id.startButton);
        Button stopButton = (Button) findViewById(R.id.stopButton);

        // Set listeners
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Async Task
                mTimerTask = new AsyncTimer(SimpleTimerActivity.this);
                mTimerTask.execute(mTimerData);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTimerData();
                if (mTimerTask != null) {
                    mTimerTask.cancel(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get application preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mTimerData = prefs.getInt(KEY_TIMER, 0);
        mTimerText.setText(String.valueOf(mTimerData));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTimerData();
        if (mTimerTask != null) {
            mTimerTask.cancel(false);
        }
    }
}
