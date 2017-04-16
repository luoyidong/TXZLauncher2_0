package com.txznet.launcher.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by TXZ-METEORLUO on 2017/2/24.
 */
public class ImageLoaderInitialize {

    public static void init(Context application) {
        DisplayImageOptions.Builder sDisplayBuilder = new DisplayImageOptions.Builder();
        DisplayImageOptions mDefaultImageOptions = sDisplayBuilder.resetViewBeforeLoading(false).bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(false).cacheOnDisk(true).build();

        ImageLoaderConfiguration.Builder configBuilder = new ImageLoaderConfiguration.Builder(application)
                .defaultDisplayImageOptions(mDefaultImageOptions).imageDownloader(new BaseImageDownloader(application)).tasksProcessingOrder(QueueProcessingType.LIFO)
                .threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(1);
        ImageLoaderConfiguration sImageLoaderConfiguration = configBuilder.build();
        ImageLoader.getInstance().init(sImageLoaderConfiguration);
    }
}