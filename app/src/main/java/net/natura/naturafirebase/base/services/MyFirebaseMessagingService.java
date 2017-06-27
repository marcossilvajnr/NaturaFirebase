package net.natura.naturafirebase.base.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.notifications.list.NotificationsActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marcos on 04/06/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String DEFAULT_TITLE = "NaturaFirebase";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
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

            if (remoteMessage.getData().containsKey("usuario")) {
                notificationBuilder.setTicker(remoteMessage.getData().get("usuario"));
                notificationBuilder.setSubText(remoteMessage.getData().get("usuario"));
            }

            if (remoteMessage.getData().containsKey("photoUrl")) {
                Bitmap bitmap = getBitmapfromUrl(remoteMessage.getData().get("photoUrl"));
                if (bitmap != null) {
                    notificationBuilder.setLargeIcon(bitmap);
                }
            }

            showNotification(remoteMessage, notificationBuilder);
        }
    }

    private void showNotification(RemoteMessage remoteMessage, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(remoteMessage.getMessageId().hashCode(), builder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);

            return makeCircleBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap makeCircleBitmap(Bitmap bitmap) {
        try {
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
