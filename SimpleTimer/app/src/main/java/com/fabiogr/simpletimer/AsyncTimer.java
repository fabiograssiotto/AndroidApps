package com.fabiogr.simpletimer;

import android.os.AsyncTask;
import android.os.SystemClock;

public class AsyncTimer extends AsyncTask<Integer, Integer, Void> {

    private SimpleTimerActivity mActivity;

    AsyncTimer(SimpleTimerActivity activity) {
        mActivity = activity;
    }

    @Override
    protected Void doInBackground(Integer... timeInit) {
        int data = timeInit[0];
        while (true) {
            if (isCancelled()) {
                break;
            }
            publishProgress(data);
            data = data + 1;
            SystemClock.sleep(1);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mActivity.updateTimerData(values[0]);
    }
}
