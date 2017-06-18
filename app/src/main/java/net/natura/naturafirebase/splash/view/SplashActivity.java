package net.natura.naturafirebase.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseActivity;
import net.natura.naturafirebase.main.view.MainActivity;
import net.natura.naturafirebase.photoregister.view.PhotoRegisterActivity;
import net.natura.naturafirebase.splash.SplashContract;
import net.natura.naturafirebase.splash.presenter.SplashPresenter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by marcos on 03/06/17.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {
    private static final int DELAY_SPLASH = 3;
    private static final int RC_SIGN_IN = 222;
    private SplashPresenter splashPresenter;

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
                splashPresenter.verifySignin();
            } else {
                finish();
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

    @Override
    public void openMain() {
        openActivity(MainActivity.newInstance(SplashActivity.this));
    }

    @Override
    public void openPhotoRegister() {
        openActivity(PhotoRegisterActivity.newInstance(SplashActivity.this));
    }

    private void setupView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void initTimmer() {
        Observable.timer(DELAY_SPLASH , TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    splashPresenter.verifySignin();
                }
            });
    }

}
