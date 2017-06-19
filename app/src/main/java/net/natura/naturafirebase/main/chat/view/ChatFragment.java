package net.natura.naturafirebase.main.chat.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.natura.naturafirebase.R;
import net.natura.naturafirebase.base.view.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by marcos on 09/06/17.
 */

public class ChatFragment extends BaseFragment {

    public static Fragment newinstance(){
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}
