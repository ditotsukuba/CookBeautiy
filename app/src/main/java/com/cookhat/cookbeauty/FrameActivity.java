
package com.cookhat.cookbeauty;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;




public class FrameActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_activity);
        Log.v("Test3:","passed.")    ;
        getSupportFragmentManager().beginTransaction().add(R.id.tab_content,new ListFragment()).commit();

    }

    public boolean onTouchEvent(MotionEvent event) {
        // ウィンドウマネージャのインスタンス取得
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        // ディスプレイのインスタンス生成
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        int Num = 4;
        int windowX = size.x;   //画面横サイズ
        int windowY = size.y;
        int pointX = (int) event.getX(); //タッチしたX座標
        int pointY = (int) event.getY();
        int endX = (int) (0.92 * windowX);  //タブ端のX座標
        int endY = (int) (0.12 * windowY);

        //Log.d("TouchEvent", "X:" + pointX + ",Y:" + pointY);
        //Log.d("window", "X:" + windowX + ",Y:" + windowY);


        if (pointY < endY) {
            if (pointX < (int) endX * (1.0 / Num)) {
                //Intent intent = new Intent(getApplication(), MainActivity.class);
                //startActivity(intent);
                // MainActivity.this.finish();
                //Log.d("main","");
            } else if ((int) (endX * (1.0 / Num)) <= pointX & pointX < (int) (endX * (2.0 / Num))) {
                Intent intent = new Intent(getApplication(), ListActivity.class);
                startActivity(intent);
                // MenuActivity.this.finish();
                //Log.d("recipe","");
            } else if ((int) (endX * (2.0 / Num)) <= pointX & pointX < (int) (endX * (3.0 / Num))) {
                /*TextView tv = new TextView(this);
                tv.setText("hanayome!!");
                setContentView(tv);*/
                Log.d("hayayome", "");
            } else if ((int) (endX * (3.0 / Num)) <= pointX & pointX < endX) {
                Intent intent = new Intent(getApplication(), KareshiActivity.class);
                startActivity(intent);
                /*TextView tv = new TextView(this);
                tv.setText("settei!!");
                setContentView(tv);*/

            }
        }

        //Log.d("TouchEvent", "X:" + event.getX() + ",Y:" + event.getY());


        //Intent intent = new Intent(getApplication(), MenuActivity.class);
        //startActivity(intent);
        //MenuActivity.this.finish();
        return super.onTouchEvent(event);
    }
}

/*
public class FrameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Test2","passed.");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frame_activity);

      //  getSupportFragmentManager().beginTransaction().add(R.id.tab_content,new ListFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.frame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
*/