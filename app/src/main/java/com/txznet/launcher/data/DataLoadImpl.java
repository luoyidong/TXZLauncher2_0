package com.txznet.launcher.data;

import android.content.Context;
import android.support.annotation.Nullable;

import com.txznet.launcher.data.api.DataLoadApi;
import com.txznet.launcher.data.model.BaseModel;

import java.util.List;

import rx.Observable;

/**
 * 数据加载器
 */
@Deprecated
public class DataLoadImpl implements DataLoadApi {
    private Context mContext;

    @Nullable
    private static DataLoadImpl INSTANCE = null;

    private DataLoadImpl(Context context) {
        mContext = context;
    }

    public static DataLoadImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataLoadImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataLoadImpl(context);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<BaseModel>> loadFromPackageManager() {
        return null;
    }

    @Override
    public Observable<List<BaseModel>> loadFromDatabase() {
        return null;
    }

    @Override
    public Observable<List<BaseModel>> loadSystemModel() {
        return null;
    }

    @Override
    public Observable<BaseModel> createModelByType(int type) {
        return null;
    }

    @Override
    public Observable<BaseModel> createModelByPackageName(String packageName, boolean isSystemApp) {
        return null;
    }

//    public List<CardModel> getAppsFromPhone() {
//        PackageManager pm = mContext.getPackageManager();
//        List<CardModel> cardApps = new ArrayList<>();
//        List<QuickBean> appQuickBeans = new ArrayList<>();
//        List<PackageInfo> pis = pm.getInstalledPackages(0);
//        for (PackageInfo info : pis) {
//            String packageName = info.packageName;
//            String name = (String) pm.getApplicationLabel(info.applicationInfo);
//            if (sCardFilter != null && sCardFilter.accessApp(packageName)) {
//                if (factory == null) {
//                    throw new NullPointerException("Factory is null！");
//                }
//                boolean isSystem = (info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
//                CardModel appInfo = factory.createCardModel(isSystem, packageName);
//                appInfo.setName(name);
//                appInfo.setPackageName(packageName);
//                cardApps.add(appInfo);
//                continue;
//            }
//
//            QuickBean quickBean = null;
//            if (sMoreAppFilter != null && sMoreAppFilter.accessApp(packageName)) {
//                quickBean = new QuickBean();
//                quickBean.packageName = packageName;
//                quickBean.type = QuickBean.TYPE_APP;
//                appQuickBeans.add(quickBean);
//                continue;
//            }
//        }
//
//        // 生成更多APP
//        MoreAppModel mad = new MoreAppModel();
//        mad.setQuickBeans(appQuickBeans);
//        cardApps.add(mad);
//
//        return cardApps;
//    }
//
//    public List<QuickBean> reloadAllApps() {
//        PackageManager pm = mContext.getPackageManager();
//        List<QuickBean> appQuickBeans = new ArrayList<>();
//        List<PackageInfo> pis = pm.getInstalledPackages(0);
//        for (PackageInfo info : pis) {
//            String packageName = info.packageName;
//            String name = (String) pm.getApplicationLabel(info.applicationInfo);
//            QuickBean quickBean = null;
//            if (sMoreAppFilter != null && sMoreAppFilter.accessApp(packageName)) {
//                quickBean = new QuickBean();
//                quickBean.packageName = packageName;
//                quickBean.type = QuickBean.TYPE_APP;
//                appQuickBeans.add(quickBean);
//            }
//        }
//        return appQuickBeans;
//    }
}