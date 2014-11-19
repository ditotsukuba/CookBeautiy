package com.cookhat.cookbeauty;

import com.cookhat.cookbeauty.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class OpeningActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.opening_activity);
        Handler hdl = new Handler();
        hdl.postDelayed(new title(), 3000);
    }

    class title implements Runnable {
        public void run() {
            Intent intent = new Intent(getApplication(), TitleActivity.class);
            startActivity(intent);
            OpeningActivity.this.finish();
        }
    }
}


