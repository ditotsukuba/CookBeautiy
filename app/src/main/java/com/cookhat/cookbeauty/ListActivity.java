package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cookhat.cookbeauty.util.CustomAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ListActivity extends Activity {
    private  DBHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
// by Matsubara

        mDbHelper = new DBHelper(this);
        mDbHelper.createEmptyDataBase(); //DB更新
        db = mDbHelper.getReadableDatabase();


        if (db != null) {
            String name_buf;
            String key_buf;
            String id_buf;
            int size = 0;


            Map<Integer, Map> columus = mDbHelper.findAll("table_recipeLists", 0, 0);
            size = columus.size();
            String name[] = new String[size];
            int id_number[] = new int[size];
            int key=0;
            final int[] listOrder = new int[size];
            Iterator iterator = columus.keySet().iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                //rowData = columus.get(o);
                name_buf = (String) columus.get(o).get("name");
                key = Integer.parseInt((String)columus.get(o).get("id"));
                id_number[key-1] = key;

                name[key-1] = name_buf;

                listOrder[key-1] = key;

            }
            Log.v("key",name[1]);

            // ListViewのインスタンスを取得
            ListView list = (ListView) findViewById(R.id.listView);
            // リストアイテムのラベルを格納するArrayListをインスタンス化
            ArrayList<String> labelList = new ArrayList<String>();

            // "List Item + ??"を20個リストに追加
            for (int i = 0; i < size; i++) {
                labelList.add(name[i]);
            }

            // Adapterのインスタンス化
            // 第三引数にlabelListを渡す
            CustomAdapter mAdapter = new CustomAdapter(this, 0, labelList);

            // リストにAdapterをセット
            list.setAdapter(mAdapter);

            // リストアイテムの間の区切り線を非表示にする
            list.setDivider(null);
            // アイテムクリック時ののイベントを追加
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int pos, long id) {

                    // 選択アイテムを取得
                    ListView listView = (ListView)parent;

                    // 表示列の料理IDの取得
                    int no = listOrder[pos];
                    //Log.v("test", "test:"+String.valueOf(test));

                    // 画面起動
                    Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                    intent.putExtra("id", no);

                    startActivity(intent);
                }
            });
        }
    }
}
