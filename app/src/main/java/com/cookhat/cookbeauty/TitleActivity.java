package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TitleActivity extends Activity {
    /** Called when the activity is first created. */
    private int touch_flg = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touch_flg == 0) {
            Intent intent = new Intent(getApplication(), FrameActivity.class);
            startActivity(intent);

            TitleActivity.this.finish();
            touch_flg = 1;
        }

        return super.onTouchEvent(event);
    }
    }
