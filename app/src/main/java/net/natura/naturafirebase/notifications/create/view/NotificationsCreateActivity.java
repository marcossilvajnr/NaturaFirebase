package net.natura.naturafirebase.notifications.create.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.notifications.model.NotificationDataModel;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.notifications.create.NotificationsCreateContract;
import net.natura.naturafirebase.notifications.create.presenter.NotificationsCreatePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marcos on 25/06/17.
 */

public class NotificationsCreateActivity extends BaseActivity implements NotificationsCreateContract.View {
    private NotificationsCreatePresenter notificationsCreatePresenter;

    @BindView(R.id.createNotificationsToolbar) Toolbar toolbar;
    @BindView(R.id.notificationTitle) EditText notificationTitle;
    @BindView(R.id.notificationMessage) EditText notificationMessage;

    public static Intent newInstance(Context activity) {
        return new Intent(activity, NotificationsCreateActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_create);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        notificationsCreatePresenter = new NotificationsCreatePresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notifications_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ok) {
            NotificationDataModel notificationDataModel = new NotificationDataModel();
            notificationDataModel.setTitle(notificationTitle.getText().toString());
            notificationDataModel.setMessage(notificationMessage.getText().toString());
            notificationDataModel.setUsuario(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            notificationDataModel.setPhotoUrl(getAppController().getUserDataModel().getUserPhotoUrl());
            notificationsCreatePresenter.createNotification(notificationDataModel);
        }

        if (item.getItemId() == R.id.menu_clear) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        notificationsCreatePresenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        notificationsCreatePresenter.detachView();
    }

    @Override
    public void createSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationsCreateActivity.this);
        builder.setTitle("Nova notificação!");
        builder.setMessage("Notificação criada com sucesso!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void createFail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationsCreateActivity.this);
        builder.setTitle("Atenção");
        builder.setMessage("Falha ao criar notificação.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.create().show();
    }
}
