package net.natura.naturafirebase.splash;

import com.firebase.ui.auth.IdpResponse;

import net.natura.naturafirebase.base.view.BaseView;

/**
 * Created by marcos on 03/06/17.
 */

public interface SplashContract {

    interface Presenter {
        void verifySignin();
        void parseAndValidateSignin(IdpResponse idpResponse);
    }

    interface View extends BaseView {
        void openLogin();
        void openMain();
    }

}
