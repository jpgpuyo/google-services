package com.google.samples.quickstart.analytics.analytics;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.samples.quickstart.analytics.R;

import android.content.Context;

public class Analytics {

    private static final String TAG = Analytics.class.getSimpleName();

    private static Analytics instance;
    private Tracker tracker;

    public synchronized static boolean init(Context context) {
        if(instance == null){
            instance = new Analytics(context);
            return true;
        }
        return false;
    }

    public static Analytics getInstance(){
        return instance;
    }

    public Analytics(Context context) {
        initTracker(context);
    }

    /**
     * Following: https://developers.google.com/analytics/devguides/collection/android/v4/
     * To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
     *
     * @return tracker
     */
    private Tracker initTracker(Context context) {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context.getApplicationContext());
            tracker = analytics.newTracker(R.xml.global_tracker);
        }
        return tracker;
    }

    public void reportAction(String category, String action) {
        if (tracker == null) throw new RuntimeException("Call init(Context context) before.");
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());
    }

    public void reportScreenView(String screenName) {
        if (tracker == null) throw new RuntimeException("Call init(Context context) before.");
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
