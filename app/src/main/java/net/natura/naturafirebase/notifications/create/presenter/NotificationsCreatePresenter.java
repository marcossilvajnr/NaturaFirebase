package net.natura.naturafirebase.notifications.create.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.natura.naturafirebase.notifications.model.NotificationDataModel;
import net.natura.naturafirebase.base.presenter.BasePresenter;
import net.natura.naturafirebase.notifications.create.NotificationsCreateContract;

/**
 * Created by marcos on 25/06/17.
 */

public class NotificationsCreatePresenter extends BasePresenter<NotificationsCreateContract.View> implements NotificationsCreateContract.Presenter {

    @Override
    public void createNotification(NotificationDataModel notificationDataModel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("notifications");

        String key = databaseReference.child("notifications").push().getKey();

        databaseReference.child(key).setValue(notificationDataModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (view != null) {
                    view.createSuccess();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (view != null) {
                    view.createFail();
                }
            }
        });
    }

}
