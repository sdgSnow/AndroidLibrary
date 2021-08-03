package com.sdg.api;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RetrofitUtil {

    public static RetrofitUtil retrofitUtil;

    public static RetrofitUtil get() {
        if (retrofitUtil == null) {
            retrofitUtil = new RetrofitUtil();
            return retrofitUtil;
        }
        return retrofitUtil;
    }

    public void request(Observable o, Observer s) {
        o.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

}
