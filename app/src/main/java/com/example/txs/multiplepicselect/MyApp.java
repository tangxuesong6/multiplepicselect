package com.example.txs.multiplepicselect;
import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
/**
 * @author txs
 * @date 2018/01/15
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Fresco 建议放在这里
        Fresco.initialize(this);
    }
}
