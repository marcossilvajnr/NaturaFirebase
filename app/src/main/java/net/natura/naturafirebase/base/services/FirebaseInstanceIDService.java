package net.natura.naturafirebase.base.services;

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

        if (refreshedToken != null && !refreshedToken.isEmpty()) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = database.getReference("users").child(firebaseUser.getUid());

            if (databaseReference != null && firebaseUser != null) {
                databaseReference.setValue(FIELD_TOKEN, refreshedToken);
            }
        }
    }
}
