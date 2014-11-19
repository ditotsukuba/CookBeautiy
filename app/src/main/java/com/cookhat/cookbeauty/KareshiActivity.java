package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cookhat.cookbeauty.util.CustomAdapter;

import java.util.ArrayList;

public class KareshiActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kareshi_activity);
/*
        // ListViewのインスタンスを取得
        ListView genreList = (ListView) findViewById(R.id.menu_genre);
        // リストアイテムのラベルを格納するArrayListをインスタンス化
        ArrayList<String> lavelGanreList = new ArrayList<String>();
        // Listにデータを入れる
        lavelGanreList.add("和");
        lavelGanreList.add("洋");
        lavelGanreList.add("中");

        // Adapterのインスタンス化
        // 第三引数にlabelListを渡す
        CustomAdapter mAdapter = new CustomAdapter(this, 0, lavelGanreList);

        // リストにAdapterをセット
        genreList.setAdapter(mAdapter);

        // リストアイテムの間の区切り線を非表示にする
        genreList.setDivider(null);

*/
    }
}
