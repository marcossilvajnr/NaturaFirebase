package net.natura.naturafirebase.notifications.create;

import net.natura.naturafirebase.notifications.model.NotificationDataModel;
import net.natura.naturafirebase.base.view.BaseView;

/**
 * Created by marcos on 25/06/17.
 */

public interface NotificationsCreateContract {

    interface Presenter {
        void createNotification(NotificationDataModel notificationDataModel);
    }

    interface View extends BaseView {
        void createSuccess();
        void createFail();
    }

}
