package com.taejun.animalsound;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by andman on 2015-12-09.
 */
public class PMWakeLock {

    public static final long INTERVAL_AWAKE_SECONDS = 7000;

    private static PowerManager.WakeLock sCpuWakeLock ;

    public static void acquireCpuWakeLock(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sCpuWakeLock = pm.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP, "hello");
        boolean isScreenon = isScreenOnDevice(context);
        if (!isScreenon){
            sCpuWakeLock.acquire();
            Log.d("PMWakeLock", "wake sCpuWakeLock = " + sCpuWakeLock);
        }
    }

    public static boolean isScreenOnDevice(Context context){
        boolean isScreenon = false;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            isScreenon = pm.isScreenOn();
        } else {
            isScreenon = pm.isInteractive();
        }
        if (isScreenon){
            KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
            isScreenon = ! keyguardManager.inKeyguardRestrictedInputMode();
        }
        return isScreenon;
    }

    public static void releaseCpuLock(Context context) {
        Log.d("PMWakeLock", "Releasing cpu wake lock");
        Log.d("PMWakeLock", "relase sCpuWakeLock = " + sCpuWakeLock);

        if (sCpuWakeLock != null) {
            if (sCpuWakeLock.isHeld())
                sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
//        PowerManager manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = manager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "hello");
//        wl.acquire();
//        wl.release();
    }
}