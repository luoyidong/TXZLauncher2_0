package com.txznet.launcher.data.repos.navi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.txznet.launcher.data.Constants;
import com.txznet.launcher.data.api.DataApi;
import com.txznet.launcher.data.api.NavApi;
import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.module.PackageManager;
import com.txznet.sdk.TXZNavManager;

import org.json.JSONObject;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by TXZ-METEORLUO on 2017/4/24.
 */
public class NavTxzApi implements DataApi<NavData, NavApi.OnNavListener>, NavApi {
    private static final String TAG = NavTxzApi.class.getSimpleName();
    private Context mContext;
    private NavData mTmpNavData;

    private OnNavListener mOnNavListener;

    @Inject
    public NavTxzApi(Context context) {
        mContext = context;
    }

    @Override
    public void initialize(OnInitListener listener) {
        if (!PackageManager.getInstance().checkAppExist(Constants.PACKAGE_NAME.TXZ_PACKAGE_NAME)) {
            if (listener != null) {
                listener.onInit(false);
            }
            return;
        }
        initConnect();
        if (listener != null) {
            listener.onInit(true);
        }
    }

    private void initConnect() {
        IntentFilter filter = new IntentFilter("com.txznet.txz.NAVI_ACTION");
        mContext.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "NavTxzApi onReceiver");
                try {
                    String navJson = intent.getStringExtra("KEY_NAVI_INFO");
                    if (TextUtils.isEmpty(navJson)) {
                        return;
                    }

                    if (mTmpNavData == null) {
                        mTmpNavData = new NavData();
                    }

                    JSONObject jsonObject = new JSONObject(navJson);
                    if (jsonObject.has("toolPKN")) {
                        mTmpNavData.navPackageName = jsonObject.optString("toolPKN");
                    }
                    if (jsonObject.has("direction")) {
                        mTmpNavData.direction = jsonObject.optInt("direction");
                    }
                    if (jsonObject.has("dirDes")) {
                        mTmpNavData.dirDes = jsonObject.optString("dirDes");
                    }
                    if (jsonObject.has("dirDistance")) {
                        mTmpNavData.dirDistance = jsonObject.optInt("dirDistance");
                    }
                    if (jsonObject.has("dirTime")) {
                        mTmpNavData.dirTime = jsonObject.optInt("dirTime");
                    }
                    if (jsonObject.has("remainDistance")) {
                        mTmpNavData.remainDistance = jsonObject.optInt("remainDistance");
                    }
                    if (jsonObject.has("remainTime")) {
                        mTmpNavData.remainTime = jsonObject.optInt("remainTime");
                    }
                    if (jsonObject.has("longitude")) {
                        mTmpNavData.longitude = jsonObject.optDouble("longitude");
                    }
                    if (jsonObject.has("latitude")) {
                        mTmpNavData.latitude = jsonObject.optDouble("latitude");
                    }
                    if (jsonObject.has("totalDistance")) {
                        mTmpNavData.totalDistance = jsonObject.optInt("totalDistance");
                    }
                    if (jsonObject.has("totalTime")) {
                        mTmpNavData.totalTime = jsonObject.optInt("totalTime");
                    }
                    if (jsonObject.has("currentLimitedSpeed")) {
                        mTmpNavData.currentLimitedSpeed = jsonObject.optInt("currentLimitedSpeed");
                    }
                    if (jsonObject.has("currentRoadName")) {
                        mTmpNavData.currentRoadName = jsonObject.optString("currentRoadName");
                    }
                    if (jsonObject.has("nextRoadName")) {
                        mTmpNavData.nextRoadName = jsonObject.optString("nextRoadName");
                    }
                    if (jsonObject.has("currentRoadType")) {
                        mTmpNavData.currentRoadType = jsonObject.optInt("currentRoadType");
                    }
                    if (jsonObject.has("currentSpeed")) {
                        mTmpNavData.currentSpeed = jsonObject.optInt("currentSpeed");
                    }

                    if (mOnNavListener != null) {
                        mOnNavListener.onNavUpdate(mTmpNavData);
                    }
                } catch (Exception e) {
                }
            }
        }, filter);
    }

    @Override
    public Observable<NavData> reqData(boolean forceReq) {
        return Observable.create(new Observable.OnSubscribe<NavData>() {

            @Override
            public void call(Subscriber<? super NavData> subscriber) {
                if (mTmpNavData != null) {
                    subscriber.onStart();
                    subscriber.onNext(mTmpNavData);
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public int getInterfacePriority() {
        return Constants.Repo_Level.Nav_Level.LEVEL_TXZ_NAV;
    }

    @Override
    public void navigateHome() {
        TXZNavManager.getInstance().navHome();
    }

    @Override
    public void navigateCompany() {
        TXZNavManager.getInstance().navCompany();
    }

    @Override
    public void register(OnNavListener listener) {
        mOnNavListener = listener;
    }

    @Override
    public void unRegister() {
        mOnNavListener = null;
    }
}
