package net.natura.naturafirebase.login.splash.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.natura.naturafirebase.base.presenter.BasePresenter;
import net.natura.naturafirebase.base.services.FirebaseAuthService;
import net.natura.naturafirebase.login.splash.SplashContract;

/**
 * Created by marcos on 03/06/17.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {

    @Override
    public void verifySignin(FirebaseUser firebaseUser) {
        if (view != null) {
            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            if (firebaseUser != null) {
                FirebaseAuthService.getInstance().setFirebaseUser(firebaseUser);

                if (firebaseUser.getPhotoUrl() != null && !firebaseUser.getPhotoUrl().toString().isEmpty()) {
                    view.openMain();
                } else {
                    view.openPhotoRegister();
                }
            } else {
                view.openLogin();
            }
        }
    }
}
