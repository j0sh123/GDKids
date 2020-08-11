package com.example.graddualkids;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notifications extends Application {
    private NotificationManagerCompat notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notifications(Context context, String title, String message) {
        notificationManager = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.notificacionn)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
              //  .setAutoCancel(true)
                .build();
      //  builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        notificationManager.notify(1,notification);

    }

}
