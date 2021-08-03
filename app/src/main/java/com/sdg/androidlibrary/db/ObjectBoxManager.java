package com.sdg.androidlibrary.db;

import android.content.Context;
import io.objectbox.BoxStore;

public class ObjectBoxManager {

    public static BoxStore mBoxStore;

    public static void init(Context context){
        mBoxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get(){
        return mBoxStore;
    }

}
