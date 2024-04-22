package com.example.myapplication.db;


import org.greenrobot.greendao.AbstractDao;


public class ChatDbManager extends BaseManager<ChatMessageBean,Long> {
    @Override
    public AbstractDao<ChatMessageBean, Long> getAbstractDao() {
        return daoSession.getChatMessageBeanDao();
    }
}
