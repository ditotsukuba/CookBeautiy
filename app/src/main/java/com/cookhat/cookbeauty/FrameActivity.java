
package com.cookhat.cookbeauty;

import android.app.Activity;
import android.app.Fragment;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;


public class FrameActivity extends FragmentActivity {





    //private RecipeFragment recipe = new RecipeFragment();
    private int state = 0; //0:home 1:list 2:kareshi 3:setting 4:recipe



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_activity);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
       //(LISTFRAGMENTあるので禁止) ListFragment list = new ListFragment();

        //Bundle list_bundle =
       // HomeFragment home = new HomeFragment();
        //AnimationFragment list = new AnimationFragment();

       //TestFragment test = new TestFragment();
       // getSupportFragmentManager().beginTransaction().add(R.id.tab_content,test);
        HomeFragment home = new HomeFragment();
        tx.add(R.id.tab_content, home);
        tx.commit();


       // Log.v("Test3:","passed.")    ;
        //getSupportFragmentManager().beginTransaction().add(R.id.tab_content,new ListFragment()).commit();

    }

    public boolean onTouchEvent(MotionEvent event) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
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
            if (pointX < (int) endX * (1.0 / Num) && (state != 0)) {
               // Log.v("Test:","Touched");
                Drawable img = getResources().getDrawable(R.drawable.tab1);
                ImageView im =(ImageView)findViewById(R.id.tab_picture);
                im.setImageDrawable(img);
               // Log.v("Test:","TabChanged");
                HomeFragment home = new HomeFragment();
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                tx.replace(R.id.tab_content,home);
                tx.addToBackStack(null);
                tx.commit();
                //Log.v("Test:","Transaction");
                state = 0;
                //Intent intent = new Intent(getApplication(), MainActivity.class);
                //startActivity(intent);
                // MainActivity.this.finish();
                //Log.d("main","");
            } else if ((int) (endX * (1.0 / Num)) <= pointX & pointX < (int) (endX * (2.0 / Num))&&(state != 1)) {
               // Log.v("Test:","Touched");
                Drawable img = getResources().getDrawable(R.drawable.tab2);
                ImageView im =(ImageView)findViewById(R.id.tab_picture);
                im.setImageDrawable(img);
               // Log.v("Test:","TabChanged");
                AnimationFragment list = new AnimationFragment();
                //Log.v("Test:","NewInstance");
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                tx.replace(R.id.tab_content,list);


                //Log.v("Test:", "Replace");
                tx.addToBackStack(null);
                //Log.v("Test:","BackStack");
                tx.commit();
                //Log.v("Test:","Transaction");
                state = 1;
               // Intent intent = new Intent(getApplication(), ListActivity.class);
                //startActivity(intent);
                // MenuActivity.this.finish();
                //Log.d("recipe","");
            } else if ((int) (endX * (2.0 / Num)) <= pointX & pointX < (int) (endX * (3.0 / Num))&&(state != 2)) {

                Drawable img = getResources().getDrawable(R.drawable.tab3);
                ImageView im =(ImageView)findViewById(R.id.tab_picture);
                im.setImageDrawable(img);
                KareshiDataFragment karedata = new KareshiDataFragment();
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                tx.replace(R.id.tab_content,karedata);
                tx.addToBackStack(null);
                tx.commit();

                state = 2;

            } else if ((int) (endX * (3.0 / Num)) <= pointX & pointX < endX && (state != 3)) {

                Drawable img = getResources().getDrawable(R.drawable.tab4);
                ImageView im =(ImageView)findViewById(R.id.tab_picture);
                im.setImageDrawable(img);

                SettingFragment setting = new SettingFragment();
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                tx.replace(R.id.tab_content,setting);
                tx.addToBackStack(null);
                tx.commit();

                state = 3;
                //Intent intent = new Intent(getApplication(), KareshiActivity.class);
                //startActivity(intent);
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

    public void changeRecipeFragment(int no)
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        if(state != 4) {

            RecipeFragment recipe = new RecipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", no);

            recipe.setArguments(bundle);
            tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            tx.replace(R.id.tab_content, recipe);

            tx.addToBackStack(null);
            tx.commit();
            state = 4;
        }
    }

    public void changeState(int s)
    {
        state = s;
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