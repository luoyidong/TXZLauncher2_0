package com.txznet.launcher.data.data;

/**
 * Created by TXZ-METEORLUO on 2017/4/14.
 */
public class MusicData {
    public static final int STATE_LOADING = 1;
    public static final int STATE_PLAY = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_STOP = 4;
    public static final int STATE_EXIT = 5;
    /**
     * 状态
     */
    public int mediaState;
    /**
     * 歌曲名
     */
    public String songTitle;
    /**
     * 艺术家
     */
    public String artistName;
    /**
     * 专辑图片
     */
    public String albumPic;
    /**
     * 当前进度
     */
    public int currProgress;
    /**
     * 总共时间
     */
    public int totalProgress;
}