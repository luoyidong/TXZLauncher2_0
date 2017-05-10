package com.txznet.launcher.mv.contract;

/**
 * Created by UPC on 2017/4/15.
 */

public interface MusicContract {

    interface View extends CardContract.View {

        void setMediaState(int state);

        void setSongTitle(String songTitle);

        void setArtistName(String artistName);

        void setAlbumPic(String picPath);

        void setCurrProgress(int progress);

        void setTotalProgress(int totalTime);
    }

    abstract class Presenter extends CardContract.Presenter<View> {

        public abstract void play();

        public abstract void pause();

        public abstract void playPre();

        public abstract void playNext();
    }
}
