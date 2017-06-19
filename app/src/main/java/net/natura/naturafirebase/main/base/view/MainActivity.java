package net.natura.naturafirebase.main.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.main.base.MainContract;
import net.natura.naturafirebase.main.base.presenter.MainPresenter;
import net.natura.naturafirebase.main.chat.view.ChatFragment;
import net.natura.naturafirebase.main.notifications.view.NotificationsFragment;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marcos on 04/06/17.
 */

public class MainActivity extends BaseActivity implements MainContract.View {
    private static final int RC_SIGN_IN = 222;
    private MainPresenter mainPresenter;
    private MainViewPagerAdapter mainViewPagerAdapter;

    @BindView(R.id.mainToolbar) Toolbar mainToolbar;
    @BindView(R.id.mainTabLayout) TabLayout mainTabLayout;
    @BindView(R.id.mainViewPager) ViewPager mainViewPager;

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mainToolbar);
        mainPresenter = new MainPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mainPresenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mainPresenter.detachView();
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
                        openLogin();
                    }
                });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                mainPresenter.fetchUserInformation();
            } else {
                if (response == null) {
                    finish();
                }
            }
        }
    }

    @Override
    public void openLogin() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setTheme(R.style.NaturaAuthTheme)
            .setLogo(R.drawable.ic_logo_natura_orange)
            .setIsSmartLockEnabled(false)
            .setProviders(Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
            )).build(), RC_SIGN_IN);
    }

    private void setupViewPager(){
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(ChatFragment.newinstance(), "Chat");
        mainViewPagerAdapter.addFragment(NotificationsFragment.newinstance(), "Notificações");
        mainViewPager.setAdapter(mainViewPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }
}
