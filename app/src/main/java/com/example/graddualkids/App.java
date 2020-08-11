package com.example.graddualkids;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel 1";
    @Override
    public void onCreate() {
        super.onCreate();
        notifications();
    }

    private void notifications() {
        createNotificationsChannels(CHANNEL_1_ID,"Chanel 1", "dont know");

    }

    private NotificationChannel createNotificationsChannels(String CHANNEL_ID, String name, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            return channel;
        }
        return null;
    }
}
