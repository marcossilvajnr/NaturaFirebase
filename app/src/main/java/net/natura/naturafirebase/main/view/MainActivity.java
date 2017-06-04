package net.natura.naturafirebase.main.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import net.natura.naturafirebase.main.MainContract;
import net.natura.naturafirebase.main.presenter.MainPresenter;

import java.util.Arrays;

/**
 * Created by marcos on 04/06/17.
 */

public class MainActivity extends BaseActivity implements MainContract.View {
    private static final int RC_SIGN_IN = 222;
    private MainPresenter mainPresenter;

    public static Intent newInstance(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter();
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
            .setProviders(Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
            )).build(), RC_SIGN_IN);
    }
}
