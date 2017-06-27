package net.natura.naturafirebase.login.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by marcos on 11/06/17.
 */

@IgnoreExtraProperties
public class UserDataModel {
    private String deviceToken;
    private String userPhotoUrl;

    public UserDataModel() {}

    public UserDataModel(String deviceToken, String userPhotoUrl) {
        this.deviceToken = deviceToken;
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }
}
