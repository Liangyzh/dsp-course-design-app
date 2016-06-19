package cn.bingoogolapple.qrcode.zxingdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by 小糯 on 2016/6/6.
 */
public class Welcome extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent mainIntent = new Intent(Welcome.this,ScanActivity.class);
                Welcome.this.startActivity(mainIntent);
                Welcome.this.finish();
            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
