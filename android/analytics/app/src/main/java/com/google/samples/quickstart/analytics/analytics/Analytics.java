package com.google.samples.quickstart.analytics.analytics;

import com.google.firebase.analytics.FirebaseAnalytics;

import android.content.Context;
import android.os.Bundle;

public class Analytics {

    private static final String TAG = Analytics.class.getSimpleName();

    private static Analytics instance;

    private FirebaseAnalytics tracker;

    public synchronized static boolean init(Context context) {
        if (instance == null) {
            instance = new Analytics(context);
            return true;
        }
        return false;
    }

    public static Analytics getInstance() {
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
    private FirebaseAnalytics initTracker(Context context) {
        if (tracker == null) {
            tracker = FirebaseAnalytics.getInstance(context.getApplicationContext());
        }
        return tracker;
    }

    public void reportAction(String category, String action) {
        if (tracker == null) {
            throw new RuntimeException("Call init(Context context) before.");
        }

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, category);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, action);

        tracker.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void reportScreenView(String screenName) {
        if (tracker == null) {
            throw new RuntimeException("Call init(Context context) before.");
        }

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);

        tracker.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }
}
