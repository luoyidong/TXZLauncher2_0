package com.txznet.launcher.db.dao;

import com.txznet.launcher.db.CardBean;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by UPC on 2017/4/16.
 */
@Singleton
public class CardDao extends BaseDao<CardBean, Long> {

    @Inject
    public CardDao() {
    }
}