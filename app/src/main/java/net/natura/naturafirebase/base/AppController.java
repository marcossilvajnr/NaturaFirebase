package net.natura.naturafirebase.base;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

import net.natura.naturafirebase.login.model.UserDataModel;

/**
 * Created by marcos on 03/06/17.
 */

public class AppController extends Application {
    private UserDataModel userDataModel;

    @Override
    public void onCreate() {
        super.onCreate();
        subscribeTopicAll();
    }

    public void subscribeTopicAll() {
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    public void setUserDataModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }
}
