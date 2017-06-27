package net.natura.naturafirebase.base;

import android.app.Application;

/**
 * Created by marcos on 03/06/17.
 */

public class AppController extends Application {
    private UserDataModel userDataModel;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserDataModel getUserDataModel() {
        return userDataModel;
    }

    public void setUserDataModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }
}
