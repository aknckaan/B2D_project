package com.b2d.b2d_project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationHandler extends FirebaseMessagingService {
    private static final String TAG ="Notification handler" ;

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
            Toast.makeText(getApplicationContext(), "Notification received!", Toast.LENGTH_SHORT).show();
            System.out.println("*************************************");
            //showNotification(remoteMessage.getData().get("message"));
            Log.d(TAG, "Notification received! ");
            //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }
    private void showNotification(String message) {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note = new Notification(R.mipmap.ic_launcher, "MYAPP", System.currentTimeMillis());
        Intent notificationIntent = new Intent(getApplicationContext(), LoginActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        note.defaults |= Notification.DEFAULT_SOUND;
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_LIGHTS;
        note.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, note);
    }
    }
