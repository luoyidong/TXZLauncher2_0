package com.txznet.launcher.db.dao;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.DatabaseConnection;
import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.db.SQLiteHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

abstract class BaseDao<T, ID extends Serializable> {
    protected final String TAG = this.getClass().getSimpleName();
    protected Dao<T, ID> mDao;

    protected BaseDao() {
        Type genType = this.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            throw new RuntimeException("this generic super class is unsupported.");
        } else {
            Type realType = ((ParameterizedType) genType).getActualTypeArguments()[0];
            try {
                mDao = OpenHelperManager.getHelper(LauncherApp.sApp, SQLiteHelper.class).getDao((Class) realType);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void save(T t) {
        try {
            mDao.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(T t) {
        try {
            mDao.createOrUpdate(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T get(ID id) {
        try {
            return mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(T t) {
        try {
            mDao.delete(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ID id) {
        try {
            mDao.delete(get(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> findAll() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeWithTransaction(TransactionContext context) {
        new TransactionWrapper(context).execute();
    }

    private class TransactionWrapper {
        private TransactionContext mContext;

        TransactionWrapper(TransactionContext context) {
            this.mContext = context;
        }

        void execute() {
            DatabaseConnection conn = null;
            try {
                conn = mDao.startThreadConnection();
                mDao.setAutoCommit(conn, false);
                mContext.executeSQL(mDao);
                mDao.commit(conn);
            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        mDao.rollBack(conn);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } finally {
                if (conn != null) {
                    try {
                        mDao.endThreadConnection(conn);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected interface TransactionContext<T, ID> {
        void executeSQL(Dao<T, ID> dao);
    }
}
