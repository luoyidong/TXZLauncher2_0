package com.txznet.launcher.module;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.txznet.launcher.LauncherApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TXZ-METEORLUO on 2017/4/9.
 */
public class PackageManager {
    private static String TAG = PackageManager.class.getSimpleName();
    private static PackageManager INSTANCE = new PackageManager();

    public static class AppInfo {
        public String appPkn;
        public String appName;
        public Drawable appIcon;
        public boolean isSystemApp;
    }

    public static PackageManager getInstance() {
        return INSTANCE;
    }

    public boolean isSystemApp(String packageName) {
        android.content.pm.PackageManager pm = LauncherApp.sApp.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES);
            return (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AppInfo getAppInfo(String packageName) {
        AppInfo appInfo = new AppInfo();
        android.content.pm.PackageManager pm = LauncherApp.sApp.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES);
            appInfo.isSystemApp = (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
            appInfo.appName = info.loadLabel(pm).toString();
            appInfo.appIcon = info.loadIcon(pm);
            appInfo.appPkn = packageName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    public boolean checkAppExist(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = LauncherApp.sApp
                    .getPackageManager()
                    .getApplicationInfo(
                            packageName,
                            android.content.pm.PackageManager.GET_UNINSTALLED_PACKAGES);
            if (info != null)
                return true;
        } catch (Exception e) {
        }
        return false;
    }

    public String getAppNameByPackageName(String packageName) {
        return getAppInfo(packageName).appName;
    }

    private boolean isLaucherApp(String packageName) {
        Intent resolveIntent;

        // 尝试查找MAIN+LAUNCHER
        resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageName);

        List<ResolveInfo> resolveinfoList = LauncherApp.sApp
                .getPackageManager().queryIntentActivities(resolveIntent, 0);

        if (resolveinfoList == null || resolveinfoList.isEmpty()) {
            return false;
        }
        return true;
    }

    public void openApp(String packageName) {
        try {
            // 默认按getLaunchIntentForPackage方式启动
            Intent in = LauncherApp.sApp.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            if (in != null) {
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                LauncherApp.sApp.startActivity(in);
                Log.d(TAG, "openApp by getLaunchIntentForPackage");
                return;
            }

            Intent resolveIntent;

            // 尝试查找MAIN+LAUNCHER
            resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            if (startAppByActivityIntent(resolveIntent)) {
                Log.d(TAG, "openApp by MAIN+LAUNCHER");
                return;
            }

            // 尝试查找MAIN
            resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(packageName);
            if (startAppByActivityIntent(resolveIntent)) {
                Log.d(TAG, "openApp by MAIN");
                return;
            }

            // 尝试查找LAUNCHER
            resolveIntent = new Intent();
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            if (startAppByActivityIntent(resolveIntent)) {
                Log.d(TAG, "openApp by LAUNCHER");
                return;
            }

            // 尝试启动第一个Activity
            try {
                PackageInfo packageinfo = LauncherApp.sApp
                        .getPackageManager().getPackageInfo(packageName, 0);
                if (packageinfo.activities.length > 0) {
                    ComponentName cn = new ComponentName(packageName,
                            packageinfo.activities[0].name);
                    resolveIntent = new Intent();
                    resolveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    resolveIntent.setComponent(cn);
                    LauncherApp.sApp.startActivity(resolveIntent);
                    Log.d(TAG, "openApp by last method");
                    return;
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    private boolean startAppByActivityIntent(Intent resolveIntent) {
        // 通过getPackageManager()的queryIntentActivities方法遍历
        try {
            List<ResolveInfo> resolveinfoList = LauncherApp.sApp
                    .getPackageManager()
                    .queryIntentActivities(resolveIntent, 0);
            if (resolveinfoList.isEmpty())
                return false;
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo == null)
                return false;
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;

            resolveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            resolveIntent.setComponent(cn);
            LauncherApp.sApp.startActivity(resolveIntent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeApp(final String packageName) {
//        if (ProcessUtil.isForeground(packageName)) {
//            returnHome();
//        }

        Runnable r = new Runnable() {
            int n = 0;

            @Override
            public void run() {
                boolean running = isAppRunning(packageName);
                Log.d(TAG, "killBackgroundProcesses:run=" + running
                        + ",count=" + n + ",pack=" + packageName);
                ActivityManager am = (ActivityManager) LauncherApp.sApp
                        .getSystemService(Context.ACTIVITY_SERVICE);
                try {
                    am.killBackgroundProcesses(packageName);
                } catch (Exception e) {
                }
                if (running) {
                    try {
                        n++;
                        am.killBackgroundProcesses(packageName);
                    } catch (Exception e) {
                    }

                    if (n < 50)
                        LauncherApp.runOnUiGround(this, 100);
                    return;
                }

                // 2秒后再检查次
                if (n > 0) {
                    n = 0;
                    LauncherApp.runOnUiGround(this, 2000);
                }
            }
        };
        LauncherApp.runOnUiGround(r, 0);
    }

    public boolean isAppRunning(String packageName) {
        try {
            ActivityManager am = (ActivityManager) LauncherApp.sApp
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo rapi : infos) {
                if (rapi.processName.equals(packageName))
                    return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 返回桌面
     */
    public void returnHome() {
        Log.d(TAG, "returnHome");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            LauncherApp.sApp.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "返回桌面错误！");
        }
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private static List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        try {
            android.content.pm.PackageManager packageManager = LauncherApp.sApp
                    .getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
                    intent, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo ri : resolveInfo) {
                names.add(ri.activityInfo.packageName);
            }
        } catch (Exception e) {
        }
        return names;
    }

    /**
     * 判断当前界面是否是桌面
     */
    public boolean isHome() {
        boolean ret = false;
        try {
            ActivityManager mActivityManager = (ActivityManager) LauncherApp.sApp.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> rti = mActivityManager
                    .getRunningTasks(1);
            ret = getHomes().contains(rti.get(0).topActivity.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // JNIHelper.logd("isHome=" + ret);
        return ret;
    }
}
