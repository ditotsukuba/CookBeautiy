package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cookhat.cookbeauty.util.CustomAdapter;
import com.cookhat.cookbeauty.util.SuggestAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class AnimationFragment extends Fragment{
    private  DBHelper mDbHelper;
    private SQLiteDatabase db;
    private FrameActivity frame;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        container.removeAllViews();
        frame.changeState(1);

        mDbHelper = new DBHelper(this.getActivity().getApplicationContext());

        mDbHelper.createEmptyDataBase(); //DB更新

        db = mDbHelper.getReadableDatabase();
         /*テストコード*/
        /*ここまで*/
        ContentValues v = new ContentValues();
/*
        v.put("name","きむりいのおにぎり");
        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = "1";
        db.update("table_recipeLists",v,whereClause,whereArgs);
*/
        mDbHelper.calcRecommend();



        View vi =inflater.inflate(R.layout.list_activity, container, false);

        //サジェストリストをどうにかする

        Map<Integer,Map> suggest = mDbHelper.findSuggest("table_recipeLists",0);
        int s = suggest.size();
        String n_buf[]= new String[s];
        double rec[] = new double[s];
        int k[] = new int[s];
        Iterator ite = suggest.keySet().iterator();
        int counter = 0;
        ArrayList<String> SuggestList = new ArrayList<String>();
        while(ite.hasNext()){
            Object o = ite.next();
            k[counter] = Integer.parseInt((String) suggest.get(o).get("id"));
            n_buf[counter] =  (String)suggest.get(o).get("name");
            rec[counter] = Double.parseDouble((String) suggest.get(o).get("recommend"));
            counter++;
        }

        final int k_pos[] = k;

        for(int i=0;i<counter;i++){
            SuggestList.add(n_buf[i]);
        }
        SuggestAdapter mAdap = new SuggestAdapter(this.getActivity().getApplicationContext(), 0, SuggestList);
        ListView l = (ListView)vi.findViewById(R.id.Suggest_list);
        l.setAdapter(mAdap);
        l.setDivider(null);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                int no = k_pos[pos];
                frame.changeRecipeFragment(no);
            }
        });




        if (db != null) {
            String name_buf;
            String key_buf;
            String id_buf;
            int size = 0;


            Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", 0, 0);
            size = columns.size();


            String name[] = new String[size];
            int id_number[] = new int[size];
            int key = 0;
            final int[] listOrder = new int[size];
            Iterator iterator = columns.keySet().iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                //rowData = columus.get(o);
                name_buf = (String)columns.get(o).get("name");
                key = Integer.parseInt((String) columns.get(o).get("id"));
                /*
                Log.v("料理名", name_buf);
                Log.v("ジャンル", (String) columns.get(o).get("genre"));
                Log.v("甘味", (String) columns.get(o).get("amami"));
                Log.v("塩味", (String) columns.get(o).get("shio"));
                Log.v("旨味", (String) columns.get(o).get("umami"));
                Log.v("酸味", (String) columns.get(o).get("acid"));
                Log.v("辛味", (String) columns.get(o).get("pain"));
                Log.v("レート", (String) columns.get(o).get("rating"));
                Log.v("オススメ度", (String) columns.get(o).get("recommend"));
                Log.v("調理済みフラグ", (String) columns.get(o).get("cooked"));
                */
                id_number[key - 1] = key;

                name[key - 1] = name_buf;

                listOrder[key - 1] = key;

            }



            // ListViewのインスタンスを取得
            ListView list = (ListView)vi.findViewById(R.id.listView);
           
            // リストアイテムのラベルを格納するArrayListをインスタンス化
            ArrayList<String> labelList = new ArrayList<String>();

            // "List Item + ??"を20個リストに追加
            for (int i = 0; i < size; i++) {
                labelList.add(name[i]);
            }

            // Adapterのインスタンス化
            // 第三引数にlabelListを渡す
            CustomAdapter mAdapter = new CustomAdapter(this.getActivity().getApplicationContext(), 0, labelList);


            // リストにAdapterをセット
            list.setAdapter(mAdapter);


            // リストアイテムの間の区切り線を非表示にする
            list.setDivider(null);
            Log.v("Test","Before Listener");
            // アイテムクリック時ののイベントを追加
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int pos, long id) {

                    // 選択アイテムを取得
                    ListView listView = (ListView) parent;

                    // 表示列の料理IDの取得
                    int no = listOrder[pos];
                    //Log.v("test", "test:"+String.valueOf(test));
                    Log.v("ID",Integer.toString(no));
                    frame.changeRecipeFragment(no);


                   /*Activity使ってた頃のコード

                    Intent intent = new Intent(getActivity(), RecipeActivity.class);
                    intent.putExtra("id", no);
                    startActivity(intent);*/


                }
            });
        }

        return vi;
    }

    @Override
    public void onAttach(Activity activity) {
        frame = (FrameActivity) activity;
        super.onAttach(activity);
    }
}


