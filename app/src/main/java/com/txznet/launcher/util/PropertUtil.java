package com.txznet.launcher.util;

import android.os.Environment;
import android.text.TextUtils;

import com.txznet.launcher.LauncherApp;
import com.txznet.launcher.module.PackageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by TXZ-METEORLUO on 2017/1/25.
 */
public class PropertUtil {
    //指定卡片类型的应用
    public static final String KEY_CARD_MUSIC = "card_music";
    public static final String KEY_CARD_NAV = "card_nav";
    public static final String KEY_CARD_CAR = "card_car";
    public static final String KEY_CARD_WEATHER = "card_weather";

    //默认显示卡片的应用包名列表
    static final String KEY_ENABLE_CARD_LIST = "enable_card_list";
    //不识别的应用包名（卡片和所有APP都不显示该应用，一般后台服务程序）
    static final String KEY_DISABLE_APP_LIST = "disable_app_list";
    //不存在该类型时是否生成假的占位卡片
    public static final String KEY_GEN_VIRTUAL_CARD = "gen_virtual_card";
    //行车记录仪实时数据目录
    static final String KEY_CARD_CAR_FILE_PATH = "card_car_file_path";
    //是否固定品牌区域
    public static final String KEY_FIX_BRAND_WIDGET = "fix_brand_widget";
    //快捷入口APP
    public static final String KEY_QUICK_APP_LIST = "quick_app_list";
    //APP列表是否用默认图片
    public static final String KEY_APP_DEFAULT_ICON = "app_default_icon";

    private static final String PATH_DEFAULT = File.separator + "system" + File.separator + "launcher" + File.separator;
    private static final String PATH_PREFIX = Environment.getExternalStorageDirectory() + File.separator + "launcher" + File.separator;
    private static final String PROPERT_NAME = "launcher.prop";

    private static final String PROPERT_FILE_PATH = PATH_PREFIX + PROPERT_NAME;
    private static final String DEFAULT_FILE_PATH = PATH_DEFAULT + PROPERT_NAME;

    private static PropertUtil sUtil = null;
    private static Properties sProperties = null;

    public static boolean FIX_BRAND_VIEW = true;

    // 要显示的卡片包名
    private static final List<String> DEFAULT_CARD_APPS = new ArrayList<>();
    // 默认快捷APP
    private static final List<String> DEFAULT_QUICK_APPS = new ArrayList<>();

    static {
        DEFAULT_CARD_APPS.add("com.baidu.naviauto");
        DEFAULT_CARD_APPS.add("com.txznet.music");
        DEFAULT_CARD_APPS.add("com.car.dvr");

        DEFAULT_QUICK_APPS.add("com.autonavi.amapautolite");
        DEFAULT_QUICK_APPS.add("com.txznet.webchat");
        DEFAULT_QUICK_APPS.add("com.txznet.music");
        DEFAULT_QUICK_APPS.add("com.car.btphone");
    }

    private PropertUtil() {
        checkInitPropertFile();

        String defVal = sProperties.getProperty(KEY_FIX_BRAND_WIDGET, "true");
        FIX_BRAND_VIEW = Boolean.parseBoolean(defVal);
    }

    public static PropertUtil getInstance() {
        if (sUtil == null) {
            synchronized (PropertUtil.class) {
                if (sUtil == null) {
                    sUtil = new PropertUtil();
                }
            }
        }
        return sUtil;
    }

    private void checkInitPropertFile() {
        sProperties = new Properties();
        try {
            sProperties.load(new FileInputStream(PROPERT_FILE_PATH));
        } catch (IOException e) {
            try {
                sProperties.load(new FileInputStream(DEFAULT_FILE_PATH));
            } catch (IOException e1) {
                try {
                    sProperties.load(LauncherApp.sApp.getAssets().open(PROPERT_NAME));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public String getObjectByKey(String key) {
        Object obj = sProperties.get(key);
        if (obj != null) {
            return (String) obj;
        }
        return "";
    }

    public String getDefaultMusic() {
        return sProperties.getProperty(KEY_CARD_MUSIC, "com.txznet.music");
    }

    public String getDefaultNav() {
        return sProperties.getProperty(KEY_CARD_NAV, "com.baidu.naviauto");
    }

    public String getDefaultRealCar() {
        return sProperties.getProperty(KEY_CARD_CAR, "com.car.dvr");
    }

    public String getDefaultWeather() {
        return sProperties.getProperty(KEY_CARD_WEATHER);
    }

    public boolean isAppDefaultIcon() {
        return Boolean.parseBoolean(sProperties.getProperty(KEY_APP_DEFAULT_ICON, "true"));
    }

    /**
     * 获取主界面卡片列表
     *
     * @return
     */
    public List<String> getEnableCards() {
        List<String> cds = getListByKey(KEY_ENABLE_CARD_LIST);
        if (cds != null && cds.size() > 0) {
            return cds;
        }
        return DEFAULT_CARD_APPS;
    }

    /**
     * 获取不支持显示的APP列表
     *
     * @return
     */
    public List<String> getDisableApps() {
        return getListByKey(KEY_DISABLE_APP_LIST);
    }

    public boolean needFixVirtualCard() {
        String val = sProperties.getProperty(KEY_GEN_VIRTUAL_CARD, "true");
        if ("true".equals(val)) {
            return true;
        }
        return false;
    }

    /**
     * 获取快捷入口APP
     *
     * @return
     */
    public List<String> getQuickApps() {
        List<String> result = new ArrayList<>();
        List<String> cds = getListByKey(KEY_QUICK_APP_LIST);
        if (cds != null && cds.size() > 0) {
            for (int i = 0; i < cds.size(); i++) {
                String pkn = cds.get(i);
                if (PackageManager.getInstance().checkAppExist(pkn)) {
                    result.add(pkn);
                }
            }
            if (result.size() > 0) {
                return result;
            }
        }

        for (int i = 0; i < DEFAULT_QUICK_APPS.size(); i++) {
            String pkn = DEFAULT_QUICK_APPS.get(i);
            if (PackageManager.getInstance().checkAppExist(pkn)) {
                result.add(pkn);
            }
        }

        return result;
    }

    private List<String> getListByKey(String key) {
        List<String> pList = null;
        String pks = sProperties.getProperty(key);
        if (!TextUtils.isEmpty(pks)) {
            String[] pArray = pks.split(";");
            if (pArray != null) {
                pList = new ArrayList<>();
                for (String p : pArray) {
                    pList.add(p);
                }
            }
        }
        return pList;
    }
}