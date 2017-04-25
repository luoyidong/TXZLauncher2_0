package com.txznet.launcher.db.dao;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.stmt.query.OrderBy;
import com.j256.ormlite.support.DatabaseConnection;
import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.db.SQLiteHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public List<T> findAllOrderBy(boolean ascending) {
        try {
            return mDao.queryBuilder().orderBy("pos", ascending).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照条件查询
     *
     * @param kvs        And键值对
     * @param groupByCol group字段
     * @param ob         整体排序方式
     * @return
     */
    public List<T> findByCondition(Map<String, Object> kvs, String groupByCol, OrderBy ob) {
        try {
            QueryBuilder qb = mDao.queryBuilder();
            if (kvs != null && !kvs.isEmpty()) {
                Where where = qb.where();
                boolean first = true;
                for (String key : kvs.keySet()) {
                    if (!first) {
                        first = true;
                        where.and();
                    }
                    where = where.eq(key, kvs.get(key));
                }
            }
            qb.groupBy(groupByCol);
            qb.orderBy(ob.getColumnName(), ob.isAscending());
            return qb.query();
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
