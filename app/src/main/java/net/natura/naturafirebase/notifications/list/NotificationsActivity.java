package net.natura.naturafirebase.notifications.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.notifications.model.NotificationDataModel;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.login.splash.view.SplashActivity;
import net.natura.naturafirebase.notifications.create.view.NotificationsCreateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marcos on 04/06/17.
 */

public class NotificationsActivity extends BaseActivity {
    @BindView(R.id.mainToolbar) Toolbar mainToolbar;
    @BindView(R.id.notificationsRecyclerView) RecyclerView notificationsRecyclerView;

    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter recyclerAdapter;

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, NotificationsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        setSupportActionBar(mainToolbar);
        setupRecycleView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_signout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        openSplash();
                    }
                });
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSplash() {
        Intent intent = new Intent(NotificationsActivity.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerAdapter.cleanup();
    }

    @OnClick(R.id.buttonNewNotifications)
    public void newNotification() {
        startActivity(NotificationsCreateActivity.newInstance(getContext()));
    }

    private void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notificationsRecyclerView.getContext(), linearLayoutManager.getOrientation());

        databaseReference = FirebaseDatabase.getInstance().getReference("notifications");
        notificationsRecyclerView.setLayoutManager(linearLayoutManager);
        notificationsRecyclerView.addItemDecoration(dividerItemDecoration);

        recyclerAdapter = new FirebaseRecyclerAdapter<NotificationDataModel, NotificationsViewHolder>(
                NotificationDataModel.class,
                R.layout.view_holder_notifications_list,
                NotificationsViewHolder.class,
                databaseReference)
        {
            @Override
            public void populateViewHolder(NotificationsViewHolder holder, NotificationDataModel dataModel, int position) {
                holder.setTitle(dataModel.getTitle());
                holder.setMessage(dataModel.getMessage());
            }
        };

        notificationsRecyclerView.setAdapter(recyclerAdapter);
    }

}
