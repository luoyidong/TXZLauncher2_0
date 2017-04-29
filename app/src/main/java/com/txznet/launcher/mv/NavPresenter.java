package com.txznet.launcher.mv;

import com.txznet.launcher.data.data.NavData;
import com.txznet.launcher.data.repos.navi.NaviLevelRepoSource;
import com.txznet.launcher.mv.contract.NavContract;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TXZ-METEORLUO on 2017/4/24.
 */
public class NavPresenter extends NavContract.Presenter {

    private NaviLevelRepoSource mRepoSource;

    @Inject
    public NavPresenter(NaviLevelRepoSource nrs) {
        mRepoSource = nrs;
    }

    @Override
    public void attachView(NavContract.View view) {
        super.attachView(view);
        loadNavInfo();
    }

    @Override
    void loadNavInfo() {
        mCompositeSubscription.add(mRepoSource
                .reqData(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NavData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(NavData navData) {
                        parseNavData(navData);
                    }
                }));
    }

    private NavData mTmpNavData;

    private void parseNavData(NavData nd) {
        if (mTmpNavData == null) {
            mTmpNavData = new NavData();
        }

        if (mTmpNavData.dirDistance != nd.dirDistance) {
            getMvpView().setDirDistance(getRemainDistance(nd.dirDistance));
        }

        if (mTmpNavData.dirDes != nd.dirDes) {
            // TODO 根据描述选择ICON
            getMvpView().setDirectionIcon(0);
        }

        if (mTmpNavData.nextRoadName != nd.nextRoadName) {
            getMvpView().setRoadName(nd.nextRoadName);
        }

        getMvpView().setRemainInfo(getRemainDistance(nd.remainDistance) + " " + getRemainTime(nd.remainTime));

        mTmpNavData = nd;
    }

    public String getRemainTime(Integer rt) {
        if (rt != null) {
            long rtd = rt;
            return getRemainTime(rtd);
        }
        return "";
    }

    public String getRemainTime(Long rt) {
        if (rt == null) {
            return "";
        }

        if (rt <= 0) {
            return "";
        }

        if (rt > 60) {
            if (rt >= 3600) {
                int r = (int) (rt % 3600);
                int h = (int) (rt / 3600);
                int m = r / 60;
                return h + "小时" + (m > 0 ? m + "分钟" : "");
            } else {
                return (rt / 60) + "分钟";
            }
        } else {
            return rt + "秒";
        }
    }

    public String getRemainDistance(Integer distance) {
        if (distance != null) {
            long dd = distance;
            return getRemainDistance(dd);
        }
        return "";
    }

    public String getRemainDistance(Long distance) {
        if (distance == null) {
            return "";
        }
        if (distance <= 0) {
            return "";
        }

        if (distance > 1000) {
            return (Math.round(distance / 100.0) / 10.0) + "公里";
        } else {
            return distance + "米";
        }
    }
}
