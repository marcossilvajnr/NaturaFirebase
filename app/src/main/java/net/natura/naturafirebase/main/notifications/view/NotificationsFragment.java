package net.natura.naturafirebase.main.notifications.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseFragment;
import net.natura.naturafirebase.main.chat.view.ChatFragment;

import butterknife.ButterKnife;

/**
 * Created by marcos on 18/06/17.
 */

public class NotificationsFragment extends BaseFragment {

    public static Fragment newinstance(){
        return new NotificationsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
