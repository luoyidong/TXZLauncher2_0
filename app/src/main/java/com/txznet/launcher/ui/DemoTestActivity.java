package com.txznet.launcher.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.txznet.launcher.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by TXZ-METEORLUO on 2017/3/22.
 */
public class DemoTestActivity extends Activity {
    public static final String TAG = DemoTestActivity.class.getSimpleName();

    @BindView(R.id.start_btn)
    Button mStartBtn;

    private TextToSpeech mTts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);

        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTts.setLanguage(Locale.ENGLISH);
            }
        });

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTts.speak("oh gril,what can i do for you", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    CompositeSubscription cs = new CompositeSubscription();

    @OnClick(R.id.start_btn)
    public void onClick() {
        cs.add(getWeathers()
                .flatMap(new Func1<Weather, Observable<Tmp>>() {
                    @Override
                    public Observable<Tmp> call(Weather weather) {
                        return Observable.from(weather.tmps);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Tmp>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Tmp tmp) {
                        Log.d(TAG, "onNext tmp:" + tmp.toString());
                    }
                }));
    }

    @OnClick(R.id.clear_btn)
    public void clearClick() {
        cs.clear();
    }

    private Observable<Weather> getWeathers() {
        return Observable.create(new Observable.OnSubscribe<Weather>() {
            @Override
            public void call(final Subscriber<? super Weather> subscriber) {
                DemoTestActivity.this.registerReceiver(new BroadcastReceiver() {
                    int count;

                    @Override
                    public void onReceive(Context context, Intent intent) {
                        count++;
                        Weather weather = new Weather();
                        weather.desc = "count" + count;
                        weather.minTmp = count;
                        weather.maxTmp = count + 10;
                        Tmp[] tmps = new Tmp[3];
                        for (int i = 0; i < tmps.length; i++) {
                            tmps[i] = new Tmp();
                            tmps[i].tmp = 1000 + i;
                            tmps[i].tmpDes = "curr tmp:" + i;
                        }
                        weather.tmps = tmps;
                        subscriber.onNext(weather);
                    }
                }, new IntentFilter("CMD_WEATHER"));
            }
        });
    }

    public static class Weather {
        public String desc;
        public int minTmp;
        public int maxTmp;
        public Tmp[] tmps;

        @Override
        public String toString() {
            return "desc:" + desc + ",minTmp:" + minTmp + ",maxTmp:" + maxTmp;
        }
    }

    public static class Tmp {
        public int tmp;
        public String tmpDes;

        @Override
        public String toString() {
            return "tmp:" + tmp + ",tmpDes:" + tmpDes;
        }
    }
}
