package net.natura.naturafirebase.base.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by marcos on 04/06/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFBInstanceIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateUserToken(refreshedToken);
    }

    private void updateUserToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

    }
}
