package com.txznet.launcher.data;

public interface Constants {
    interface PACKAGE_NAME {
        String NAVI_PACKAGE_NAME = "";
        String MUSIC_PACKAGE_NAME = "com.txznet.music";
        String WEAHTER_PACKAGE_NAME = "";
        String VIDIO_PACKAGE_NAME = "";
        String MORE_PACKAGE_NAME = "";
    }

    interface Repo_Level {

        // 音乐工具优先级
        interface Music_Level {
            // 同听
            int LEVEL_TT = 1;
        }

        // 导航优先级
        interface Nav_Level {
            // 同行者导航
            int LEVEL_TXZ_NAV = 1;
        }

        // 天气优先级
        interface Weather_Level {
            // 同行者天气
            int LEVEL_TXZ_WEATHER = 1;
        }
    }

    interface KeyValueConstants {

        interface KeyValueMusic {
            String KEY_SONG = "song";
            String KEY_ARTS = "art";
            String KEY_STATUS = "status";
            String KEY_PIC = "picUri";
            String KEY_PROGRESS = "progress";
            String KEY_TOTALTIME = "totalTime";

            int VALUE_PLAY = 1;
            int VALUE_PAUSE = 2;
            int VALUE_STOP = 3;
            int VALUE_PRE = 4;
            int VALUE_NEXT = 5;
            int VALUE_LOADING = 6;

            String TXZ_STATUS_CHANGE_ACTION = "com.txznet.music.action.PLAY_STATUS_CHANGE";
            String TXZ_MUSIC_INFO_ACTION = "com.txznet.music.action.PLAY_AUDIO";
            String TXZ_MUSIC_PROPRESS_ACTION = "com.txznet.music.Action.Progress";
            String TXZ_MUSIC_PLAY_ACTION = "com.txznet.music.play";
            String TXZ_MUSIC_PAUSE_ACTION = "com.txznet.music.pause";
            String TXZ_MUSIC_REQUEST_ACTION = "com.txznet.music.query";
            String TXZ_MUSIC_NEXT_ACTION = "com.txznet.music.next";
        }
    }
}