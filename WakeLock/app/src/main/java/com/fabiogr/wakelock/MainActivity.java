package com.fabiogr.wakelock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button grabWkLockBtn;
    private Button rlsWkLockBtn;
    private PowerManager pm;
    private PowerManager.WakeLock wkLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grabWkLockBtn = findViewById(R.id.wakeGetBtn);
        rlsWkLockBtn = findViewById(R.id.wakeRlsBtn);

        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        grabWkLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acquire a wake lock, keep device on.
                wkLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
                wkLock.acquire(10000);
                Toast.makeText(getApplicationContext(), "WakeLock Acquired", Toast.LENGTH_SHORT).show();
            }
        });
        rlsWkLockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wkLock.isHeld()) {
                    wkLock.release();
                    Toast.makeText(getApplicationContext(), "WakeLock Released", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wkLock.isHeld()) {
            wkLock.release();
        }
    }
}
