package net.natura.naturafirebase.base.view;

import android.content.Context;
import android.content.DialogInterface;

import net.natura.naturafirebase.base.AppController;

/**
 * Created by br95049 on 9/19/16.
 */

public interface BaseView {
    AppController getAppController();
    Context getContext();
    void showSimpleYesNoDialog(String title, String message, DialogInterface.OnClickListener positiveOnClickListener, DialogInterface.OnClickListener negativeOnClickListener);
    void showToast(String message, int duration);
}
