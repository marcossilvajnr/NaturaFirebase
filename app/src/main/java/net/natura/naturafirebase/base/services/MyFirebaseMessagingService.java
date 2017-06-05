package net.natura.naturafirebase.base.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.main.view.MainActivity;

/**
 * Created by marcos on 04/06/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String DEFAULT_TITLE = "NaturaFirebase";
    private static final String GROUP_KEY = "group";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
            notificationBuilder.setSmallIcon(R.drawable.ic_notification);
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
            notificationBuilder.setContentTitle(remoteMessage.getNotification().getTitle() != null ? remoteMessage.getNotification().getTitle() : DEFAULT_TITLE);
            notificationBuilder.setContentInfo(remoteMessage.getNotification().getBody());
            notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setGroup(remoteMessage.getCollapseKey());
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(remoteMessage.getMessageId().hashCode(), notificationBuilder.build());
        }
    }
}
