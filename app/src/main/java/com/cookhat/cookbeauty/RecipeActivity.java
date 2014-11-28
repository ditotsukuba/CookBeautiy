package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;


public class RecipeActivity extends Activity {
    private  DBHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_activity);
        // intentからパラメータ取得
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        mDbHelper = new DBHelper(this);
        db = mDbHelper.getReadableDatabase();
        if(db==null) return;

        Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", id, 0);
        Map<String, String> rowData;
        if(columns.size()==1) {
            rowData = columns.get(0);
            TextView titleView = (TextView)findViewById(R.id.menuName);
            titleView.setText(rowData.get("name"));
            //Log.v("test", rowData.get("name"));

        }
        Drawable img = getResources().getDrawable(R.drawable.tab1);//とりあえずエラー吐かないように修正
        ImageView im =(ImageView)findViewById(R.id.menuPicture);
        im.setImageDrawable(img);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
