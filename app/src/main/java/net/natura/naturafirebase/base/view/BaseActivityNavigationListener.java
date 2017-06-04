package net.natura.naturafirebase.base.view;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by marcos on 03/06/17.
 */

public interface BaseActivityNavigationListener {
    void openFragment(int container, Fragment fragment, String TAG);
    void openActivity(Intent intent);
    void openActivityForResult(Intent intent, int requestId);
}
