package com.sandyzfeaklab.breakdown_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sandyzfeaklab.breakdown_app.activities.Oil_consumption;

public class FireBase_Notification extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();


        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Intent intent1 = new Intent(getApplicationContext(), Oil_consumption.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),
                    0,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Heads Up Notification",
                            NotificationManager.IMPORTANCE_HIGH);

            getSystemService(NotificationManager.class).createNotificationChannel(channel);

            Notification.Builder notification =
                    new Notification.Builder(this, CHANNEL_ID)
                            .setContentTitle(title)
                            .setContentText(text)
                            .setContentIntent(intent)
                            .setSmallIcon(R.drawable.applogo)
                            .setAutoCancel(true);

            NotificationManagerCompat.from(this).notify(1, notification.build());


        } else {

            Intent intent1 = new Intent(getApplicationContext(), Oil_consumption.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),
                    0,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder notification =
                    new Notification.Builder(this)
                            .setContentTitle(title)
                            .setContentText(text)
                            .setContentIntent(intent)
                            .setSmallIcon(R.drawable.applogo)
                            .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notification.build());
        }


    }
}
