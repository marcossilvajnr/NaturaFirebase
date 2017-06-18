package net.natura.naturafirebase.login.splash;

import net.natura.naturafirebase.base.view.BaseView;

/**
 * Created by marcos on 03/06/17.
 */

public interface SplashContract {

    interface Presenter {
        void verifySignin();
    }

    interface View extends BaseView {
        void openLogin();
        void openMain();
        void openPhotoRegister();
    }

}
