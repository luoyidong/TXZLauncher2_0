package com.txznet.launcher.db.dao;

import com.txznet.launcher.db.CardBean;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by UPC on 2017/4/16.
 */
@Singleton
public class CardDao extends BaseDao<CardBean, Long> {

    private static CardDao INSTANCE = new CardDao();

    public static CardDao getInstance() {
        return INSTANCE;
    }

    public CardDao() {
    }
}