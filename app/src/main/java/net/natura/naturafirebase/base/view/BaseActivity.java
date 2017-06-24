package net.natura.naturafirebase.base.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.AppController;

/**
 * Created by marcos on 03/06/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView, BaseActivityNavigationListener {

    @Override
    public AppController getAppController() {
        return ((AppController) getApplication());
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }

    @Override
    public void showToast(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }

    @Override
    public void showSimpleYesNoDialog(String title, String message, DialogInterface.OnClickListener positiveOnClickListener, DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getContext().getResources().getString(R.string.alert_button_yes), positiveOnClickListener);
        builder.setNegativeButton(getContext().getResources().getString(R.string.alert_button_no), negativeOnClickListener);
        builder.create().show();
    }

    @Override
    public void openFragment(int container, Fragment fragment, String TAG) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(TAG);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void openActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void openActivityForResult(Intent intent, int requestId) {
        startActivityForResult(intent, requestId);
    }

}
