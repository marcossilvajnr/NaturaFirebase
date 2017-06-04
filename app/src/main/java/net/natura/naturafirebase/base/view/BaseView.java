package net.natura.naturafirebase.base.view;

import android.content.Context;

import net.natura.naturafirebase.base.AppController;

/**
 * Created by br95049 on 9/19/16.
 */

public interface BaseView {
    AppController getAppController();
    Context getContext();
    void showToast(String message, int duration);
}
