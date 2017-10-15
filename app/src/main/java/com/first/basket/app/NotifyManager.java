package com.first.basket.app;

import com.first.basket.bean.NotifyMsgEntity;
import com.first.basket.utils.UIUtils;

import java.util.Observable;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class NotifyManager extends Observable {
    private static NotifyManager mNotifyManager;

    public static final int TYPE_DFH_SUB_STATE_CHANGED = 172;

    /**
     * 获取通知管理器
     *
     * @return
     */
    public static NotifyManager getNotifyManager() {
        if (mNotifyManager == null) {
            mNotifyManager = new NotifyManager();
        }
        return mNotifyManager;
    }

    private NotifyManager() {

    }

    /**
     * 事件发生后通知监听者
     *
     * @param code :事件代码号
     */
    public void notifyChange(int code) {
        NotifyMsgEntity msgEntity = new NotifyMsgEntity();
        msgEntity.setCode(code);
        notifyChange(msgEntity);
    }

    /**
     * 事件发生后通知监听者
     *
     * @param msgEntity 需要发送的消息数据
     */
    public void notifyChange(final NotifyMsgEntity msgEntity) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(msgEntity);
            }
        });
    }

}
