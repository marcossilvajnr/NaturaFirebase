package net.natura.naturafirebase.login.photoregister;

import android.graphics.Bitmap;

import net.natura.naturafirebase.base.view.BaseView;

/**
 * Created by marcos on 11/06/17.
 */

public interface PhotoRegisterContract {

    interface Presenter {
        void uploadUserProfilePhoto(Bitmap bitmap, String userid);
    }

    interface View extends BaseView {
        void setupImagePreview();
        void setupImageEmptyPreview();
        void startUpload();
        void showProgressUpload(Integer progress);
        void showErrorUpload();
        void showSuccessUpload();
    }
}
