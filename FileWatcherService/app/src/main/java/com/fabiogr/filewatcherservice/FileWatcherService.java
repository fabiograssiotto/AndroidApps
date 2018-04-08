package com.fabiogr.filewatcherservice;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

public class FileWatcherService extends Service {

    public static final String EXTRA_PATH = "EXTRA_PATH";

    private static FileObserver mObserver;
    private static String TAG = "FileWatcherService";

    private void startWatcher(String path) {
        mObserver = new FileObserver(path) {
            @Override
            public void onEvent(int event, @Nullable String path) {
                if (event == FileObserver.CREATE) {
                    // New file created on folder
                    Log.w(TAG, "FileWatcherService: new file detected: " + path);
                    // Let activity know?
                }
            }
        };
        mObserver.startWatching();
    }

    private void stopWatcher() {
        mObserver.stopWatching();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // get path
        String path = intent.getStringExtra(EXTRA_PATH);
        // start watching the folder
        startWatcher(path);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopWatcher();
    }
}
