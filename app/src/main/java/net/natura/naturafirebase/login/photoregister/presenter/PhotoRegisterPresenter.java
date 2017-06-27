package net.natura.naturafirebase.login.photoregister.presenter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.natura.naturafirebase.base.model.UserDataModel;
import net.natura.naturafirebase.base.presenter.BasePresenter;
import net.natura.naturafirebase.login.photoregister.PhotoRegisterContract;

import java.io.ByteArrayOutputStream;

/**
 * Created by marcos on 11/06/17.
 */

public class PhotoRegisterPresenter extends BasePresenter<PhotoRegisterContract.View> implements PhotoRegisterContract.Presenter {

    @Override
    public void uploadUserProfilePhoto(Bitmap bitmap, String userid) {
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        FirebaseOptions firebaseOptions = firebaseApp.getOptions();

        if (firebaseOptions != null) {
            initUpload(bitmap, userid, firebaseOptions.getStorageBucket());
        }
    }

    private void initUpload(Bitmap bitmap, String userid, String bucket) {
        if (view != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://" + bucket + "/users/");
            StorageReference userPhotoRef = storageRef.child(userid + ".jpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            view.startUpload();

            UploadTask uploadTask = userPhotoRef.putBytes(data);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    exception.printStackTrace();
                    if (view != null) {
                        view.showErrorUpload();
                    }

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    updateUserData(downloadUrl.toString());
                    view.showSuccessUpload();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    if (view != null) {
                        Long progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        view.showProgressUpload(progress.intValue());
                    }
                }
            });
        }
    }

    private void updateUserData(final String userPhotoUrl) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("users").child(firebaseUser.getUid());

        if (databaseReference != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);
                    userDataModel.setUserPhotoUrl(userPhotoUrl);

                    if (view != null) {
                        view.getAppController().setUserDataModel(userDataModel);
                    }

                    databaseReference.setValue(userDataModel);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }
}
