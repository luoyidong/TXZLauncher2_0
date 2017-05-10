package com.txznet.launcher.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.R;
import com.txznet.launcher.module.PackageManager;
import com.txznet.launcher.mv.AppPresenter;
import com.txznet.launcher.mv.contract.AppContract;
import com.txznet.launcher.ui.MainActivity;
import com.txznet.launcher.ui.model.UiApp;
import com.txznet.launcher.ui.view.AppRecyclerView;

import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/5/3.
 */
public class AppCardView extends ExpandCardView<AppPresenter> implements AppContract.View {

    public AppCardView(Context context) {
        super(context);
    }

    public AppCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void inject(MainActivity ma) {
        ma.getComponent().inject(this);
    }

    @Override
    public View getExpandableView() {
        return findViewById(R.id.all_app_rv);
    }

    @Override
    public int getLayoutId() {
        return R.layout.card_apps_ly;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        AppRecyclerView arv = (AppRecyclerView) findViewById(R.id.all_app_rv);
        arv.setSpaceClickListener(new AppRecyclerView.OnSpaceClickListener() {
            @Override
            public void onClick() {
                // TODO 取消编辑
            }
        });

        hideApps();
    }

    @Override
    public void showQuickApps(List<UiApp> appInfos) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.slide_app_recycle);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new QuickAppAdapter(appInfos);
            recyclerView.setAdapter(adapter);
        } else if (adapter instanceof QuickAppAdapter) {
            ((QuickAppAdapter) adapter).replaceData(appInfos);
        }
    }

    @Override
    public List<UiApp> getQuickApps() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.slide_app_recycle);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof QuickAppAdapter) {
            return ((QuickAppAdapter) adapter).getUiApps();
        }
        return null;
    }

    @Override
    public void showAllApps(List<UiApp> apps) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_app_rv);
        if (apps == null || apps.size() < 1) {
            recyclerView.setVisibility(GONE);
            return;
        } else {
            recyclerView.setVisibility(VISIBLE);
        }

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new AppAdapter(apps);
            recyclerView.setAdapter(adapter);
        } else if (adapter instanceof AppAdapter) {
            ((AppAdapter) adapter).replaceData(apps);
        }
    }

    @Override
    public List<UiApp> getAllApps() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_app_rv);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null && adapter instanceof AppAdapter) {
            return ((AppAdapter) adapter).getUiApps();
        }
        return null;
    }

    @Override
    public void hideApps() {
        retractView();
    }

    @Override
    public void visibleApps() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_app_rv);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null || adapter.getItemCount() <= 0) {
            return;
        }

        expandView();
    }

    @Override
    public boolean isAppsVisible() {
        return isExpand();
    }

    private class QuickAppAdapter extends RecyclerView.Adapter<QuickAppAdapter.QuickAppHolder> {
        protected List<UiApp> mAppInfos;

        public QuickAppAdapter(List<UiApp> appInfos) {
            this.mAppInfos = appInfos;
        }

        public void replaceData(List<UiApp> appInfos) {
            this.mAppInfos = appInfos;
            this.notifyDataSetChanged();
        }

        public List<UiApp> getUiApps() {
            return mAppInfos;
        }

        @Override
        public QuickAppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new QuickAppHolder(View.inflate(parent.getContext(), R.layout.quick_app_item_ly, null));
        }

        @Override
        public void onBindViewHolder(QuickAppHolder holder, int position) {
            UiApp info = mAppInfos.get(position);

            final View itemView = holder.itemView;
            holder.itemView.setTag(R.id.flag_bindApp, info);
            ((ImageView) itemView.findViewById(R.id.app_icon_iv)).setImageDrawable(info.iconDrawable);
            ((TextView) itemView.findViewById(R.id.app_name_tv)).setText(info.name);
        }

        @Override
        public int getItemCount() {
            return mAppInfos != null ? mAppInfos.size() : 0;
        }

        public class QuickAppHolder extends RecyclerView.ViewHolder {

            public QuickAppHolder(final View itemView) {
                super(itemView);

                itemView.findViewById(R.id.close_iv).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        final View cIv = itemView.findViewById(R.id.close_iv);
                        cIv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        Rect touchRect = new Rect();
                        touchRect.left = cIv.getLeft() - cIv.getWidth() * (3 / 2);
                        touchRect.top = cIv.getTop() - cIv.getHeight() * (3 / 2);
                        touchRect.right = cIv.getRight() + cIv.getWidth() * (3 / 2);
                        touchRect.bottom = cIv.getBottom() + cIv.getHeight() * (3 / 2);
                        itemView.findViewById(R.id.icon_ly).setTouchDelegate(new TouchDelegate(touchRect, cIv));
                    }
                });

                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(QuickAppHolder.this);
                    }
                });

                itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onItemLongClick(QuickAppHolder.this);
                    }
                });
            }
        }
    }

    private class AppAdapter extends QuickAppAdapter {

        public AppAdapter(List<UiApp> appInfos) {
            super(appInfos);
        }

        @Override
        public void onBindViewHolder(final QuickAppHolder holder, int position) {
            super.onBindViewHolder(holder, position);

            UiApp uiApp = mAppInfos.get(position);
            if (uiApp.isShowClose) {
                holder.itemView.findViewById(R.id.close_iv).setVisibility(VISIBLE);
            } else {
                holder.itemView.findViewById(R.id.close_iv).setVisibility(GONE);
            }

            holder.itemView.findViewById(R.id.close_iv).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCloseClick(holder);
                }
            });
        }
    }

    /**
     * 点击了关闭按钮
     *
     * @param holder
     */
    private void onCloseClick(RecyclerView.ViewHolder holder) {
        int pos = holder.getAdapterPosition();
        Log.d("AppCardView", "onItemClick:" + pos);
        UiApp uiApp = (UiApp) holder.itemView.getTag(R.id.flag_bindApp);
        if (pos == -1) {
            return;
        }

        Uri packageURI = Uri.parse("package:" + uiApp.packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LauncherApp.sApp.startActivity(uninstallIntent);
    }

    /**
     * 点击了一个APP
     *
     * @param holder
     */
    private void onItemClick(RecyclerView.ViewHolder holder) {
        int pos = holder.getAdapterPosition();
        if (pos != -1) {
            UiApp uiApp = (UiApp) holder.itemView.getTag(R.id.flag_bindApp);
            if (uiApp != null) {
                PackageManager.getInstance().openApp(uiApp.packageName);
            }
        }
    }

    /**
     * 长按一个APP
     *
     * @param holder
     */
    private boolean onItemLongClick(RecyclerView.ViewHolder holder) {
        return false;
    }

    public void alphaChild(View view) {
        Object obj = view.getTag(R.id.flag_dragTag);
        if (obj == null || !(boolean) obj) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
            view.setAlpha(0.5f);
            view.setTag(R.id.flag_dragTag, true);
        }
    }

    public void restoreChild(View view) {
        Object obj = view.getTag(R.id.flag_dragTag);
        if (obj != null && (boolean) obj) {
            view.setScaleY(1.0f);
            view.setScaleX(1.0f);
            view.setAlpha(1.0f);
            view.setTag(R.id.flag_dragTag, false);
        }
    }
}