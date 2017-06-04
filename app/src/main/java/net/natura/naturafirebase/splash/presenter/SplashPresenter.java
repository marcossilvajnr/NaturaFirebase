package net.natura.naturafirebase.splash.presenter;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import net.natura.naturafirebase.base.presenter.BasePresenter;
import net.natura.naturafirebase.splash.SplashContract;

/**
 * Created by marcos on 03/06/17.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    @Override
    public void verifySignin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            view.openMain();
        } else {
            view.openLogin();
        }
    }

    @Override
    public void parseAndValidateSignin(IdpResponse idpResponse) {

    }
}
