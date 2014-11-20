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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(getApplication(), FrameActivity.class);
        startActivity(intent);
        Log.v("Test1:","passed.");
        TitleActivity.this.finish();
        Log.v("Test2:","passed.");
        return super.onTouchEvent(event);
    }
    }
