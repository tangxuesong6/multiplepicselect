package com.example.txs.multiplepicselect;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author txs
 * @date 2018/01/15
 */

public class MyApp extends Application {
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco 建议放在这里
        Fresco.initialize(this);
    }
}


