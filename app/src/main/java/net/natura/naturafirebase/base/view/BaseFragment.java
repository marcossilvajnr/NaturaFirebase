package net.natura.naturafirebase.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import net.natura.naturafirebase.base.AppController;

/**
 * Created by marcos on 03/06/17.
 */

public class BaseFragment extends Fragment implements BaseView, BaseFragmentNavigationListener {
    protected BaseActivityNavigationListener baseActivityNavigationListener;

    @Override
    public AppController getAppController() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            return ((AppController) fragmentActivity.getApplication());
        } else {
            return null;
        }
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showToast(String message, int duration) {
        Toast.makeText(getContext(), message, duration).show();
    }

    @Override
    public void backNavigation() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void setBaseNavigationListener(BaseActivityNavigationListener baseActivityNavigationListener) {
        this.baseActivityNavigationListener = baseActivityNavigationListener;
    }
}
