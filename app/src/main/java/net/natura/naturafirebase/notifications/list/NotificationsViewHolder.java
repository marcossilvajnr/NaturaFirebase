package net.natura.naturafirebase.notifications.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.natura.naturafirebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marcos on 25/06/17.
 */

public class NotificationsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.vhNotificationsTitle) TextView vhNotificationsTitle;
    @BindView(R.id.vhNotificationsMessage) TextView vhNotificationsMessage;

    public NotificationsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setTitle(String title) {
        vhNotificationsTitle.setText(title);
    }

    public void setMessage(String message) {
        vhNotificationsMessage.setText(message);
    }
}
