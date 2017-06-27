package net.natura.naturafirebase.base.services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by marcos on 04/06/17.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String FIELD_TOKEN = "deviceToken";
    public FirebaseInstanceIDService() {}

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("refreshedToken", refreshedToken);
    }
}
