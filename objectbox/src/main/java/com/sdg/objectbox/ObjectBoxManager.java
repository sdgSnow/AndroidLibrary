package com.sdg.objectbox;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import io.objectbox.BoxStore;

public class ObjectBoxManager {

    public static BoxStore mBoxStore;

    public static void init(Context context){
        File externalFilesDir = context.getApplicationContext().getExternalFilesDir("");
        String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/003/";
        File file = new File(s);
        mBoxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .baseDirectory(file)
                .name("test")
                .build();
    }

    public static BoxStore get(){
        return mBoxStore;
    }

}
