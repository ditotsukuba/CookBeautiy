package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.cookhat.cookbeauty.ListActivity;
import com.cookhat.cookbeauty.R;


public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
