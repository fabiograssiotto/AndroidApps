package com.fabiogr.filewatcherservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class BlankActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private String mWatchFolderPath;

    private void setUpFolderPath() {
        // Set up file path for the service.
        String path = Environment.getExternalStorageDirectory().getPath() + "/test";
        File dir = new File(path);

        try {
            // Links do not work for the FileObserver, so get the canonical path.
            mWatchFolderPath = dir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!dir.exists()) {
            boolean ret = dir.mkdirs();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, so request it.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            setUpFolderPath();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
           && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpFolderPath();
        }
    }

    @Override
    public void onClick(View v) {
        Intent svcIntent = new Intent(getApplicationContext(), FileWatcherService.class);
        svcIntent.putExtra(FileWatcherService.EXTRA_PATH, mWatchFolderPath);
        startService(svcIntent);
    }
}
