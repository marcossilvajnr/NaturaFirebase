package net.natura.naturafirebase.login.splash.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.main.base.view.MainActivity;
import net.natura.naturafirebase.login.photoregister.view.PhotoRegisterActivity;
import net.natura.naturafirebase.login.splash.SplashContract;
import net.natura.naturafirebase.login.splash.presenter.SplashPresenter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by marcos on 03/06/17.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    private static final int DELAY_SPLASH_IN_SECONDS = 3;
    private static final int RC_SIGN_IN = 222;
    private SplashPresenter splashPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashPresenter = new SplashPresenter();
        setupView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initTimmer();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        splashPresenter.attachView(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        splashPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                splashPresenter.verifySignin(FirebaseAuth.getInstance().getCurrentUser());
            } else {
                IdpResponse response = IdpResponse.fromResultIntent(data);

                // User pressed back button
                if (response == null) {
                    showSimpleYesNoDialog(
                        getResources().getString(R.string.alert_title_default),
                        getResources().getString(R.string.alert_message_login_backbutton),
                        positiveBackButtonOnClickListener,
                        negativeBackButtonOnClickListener);
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast(getResources().getString(R.string.alert_message_error_no_connection), Toast.LENGTH_LONG);
                    return;
                } else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showToast(getResources().getString(R.string.alert_message_error_default), Toast.LENGTH_LONG);
                    return;
                }
            }
        }
    }

    @Override
    public void openLogin() {
        hideLoading();

        startActivityForResult(AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.NaturaAuthTheme)
            .setLogo(R.drawable.ic_logo_natura_orange)
            .setIsSmartLockEnabled(false)
            .setProviders(Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
            )).build(), RC_SIGN_IN);
    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void openMain() {
        hideLoading();
        openActivity(MainActivity.newInstance(SplashActivity.this));
    }

    @Override
    public void openPhotoRegister() {
        hideLoading();
        openActivity(PhotoRegisterActivity.newInstance(SplashActivity.this));
    }

    private void setupView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void initTimmer() {
        Observable.timer(DELAY_SPLASH_IN_SECONDS, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    splashPresenter.verifySignin(FirebaseAuth.getInstance().getCurrentUser());
                }
            });
    }

    private DialogInterface.OnClickListener positiveBackButtonOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            openLogin();
        }
    };

    private DialogInterface.OnClickListener negativeBackButtonOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };
}
