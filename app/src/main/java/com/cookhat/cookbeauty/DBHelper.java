package com.cookhat.cookbeauty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by matsubara on 2014/09/04.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final  String TAG = "DBHelper";

    private static String DB_NAME = "CookBook.db";
    private static String DB_NAME_ORGINAL = "CookBookOriginal.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private final File mDatabasePath;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mContext = context;
        mDatabasePath = mContext.getDatabasePath(DB_NAME);
    }

    public void WriteFlag(int id,int flag)
    {
        ContentValues f = new ContentValues();
        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        f.put("cooked",flag);
        mDatabase.update("table_recipeLists", f, whereClause, whereArgs);
    }

    public void calcRecommend()
    {
        int size = 0;
        int i = 0;
        int j = 0;
        final int CHECK_PARAMS = 7;
        double reco = 0;
        //SQLiteDatabase d =  getReadableDatabase();
        String dbPath = mDatabasePath.getAbsolutePath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        Map<Integer,Map>data = findAll("table_recipeLists",0,0);
        size = data.size();


        //Iterator ite = data.keySet().iterator();

        /*一時格納用変数*/
        double rate_buf[],rate[];
        rate_buf = new double [7];//計算用
        rate = new double [7];//[0]:レーティング,[1]:ジャンル,[2]甘み,[3]塩味,[4]旨み,[5]酸味[6]辛み
        /*ここまで*/
        for(i=size-1;i>=0;i--){
            reco = 0;

            rate[0] = Double.parseDouble((String)data.get(i).get("rating"));
            rate[1] = Double.parseDouble((String)data.get(i).get("genre"));
            rate[2] = Double.parseDouble((String)data.get(i).get("amami"));
            rate[3] = Double.parseDouble((String)data.get(i).get("shio"));
            rate[4] = Double.parseDouble((String)data.get(i).get("umami"));
            rate[5] = Double.parseDouble((String)data.get(i).get("acid"));
            rate[6] = Double.parseDouble((String)data.get(i).get("pain"));
            Iterator ite_now = data.keySet().iterator();
            int count = 0;
            while(ite_now.hasNext())
            {

                double reco_buf = 0;
                if(true){
                    Object obj_buf = ite_now.next();
                    rate_buf[0] = Double.parseDouble((String)data.get(obj_buf).get("rating"));
                    rate_buf[1] = Double.parseDouble((String)data.get(obj_buf).get("genre"));
                    rate_buf[2] = Double.parseDouble((String)data.get(obj_buf).get("amami"));
                    rate_buf[3] = Double.parseDouble((String)data.get(obj_buf).get("shio"));
                    rate_buf[4] = Double.parseDouble((String)data.get(obj_buf).get("umami"));
                    rate_buf[5] = Double.parseDouble((String)data.get(obj_buf).get("acid"));
                    rate_buf[6] = Double.parseDouble((String)data.get(obj_buf).get("pain"));
                    //5Stars
                    if(rate_buf[0] == 5.0){
                        for(j=2;j<CHECK_PARAMS;j++){
                            if(rate[j] == rate_buf[j]){
                                reco_buf += 5.0;
                            }
                            else if(Math.abs(rate[j] - rate_buf[j]) == 1.0){
                                reco_buf += 1.0;
                            }
                        }
                       // count++;
                        //Log.v("IteratorID="+(String)data.get(i).get("id"),Integer.toString(count));
                        reco_buf *=2.0;

                    }
                    //4Stars
                    else if(rate_buf[0] == 4.0){
                        for(j=2;j<CHECK_PARAMS;j++){
                            if(rate[j] == rate_buf[j]){
                                reco_buf += 5.0;
                            }
                            else if(Math.abs(rate[j] - rate_buf[j]) == 1.0){
                                reco_buf += 1.0;
                            }
                        }
                        reco_buf *= 2.0;


                    }
                    //3Stars
                    else if(rate_buf[0] == 3.0){
                        for(j=2;j<CHECK_PARAMS;j++){
                            if(rate[j] == rate_buf[j]){
                                reco_buf += 5.0;
                            }
                            else if(Math.abs(rate[j] - rate_buf[j]) == 1.0){
                                reco_buf += 1.0;
                            }
                        }
                        reco_buf *= 0.5;

                    }
                    //2Stars
                    else if(rate_buf[0] == 2.0){
                        for(j=2;j<CHECK_PARAMS;j++){
                            if(rate[j] == rate_buf[j]){
                                reco_buf += 5.0;
                            }
                            else if(Math.abs(rate[j] - rate_buf[j]) == 1.0){
                                reco_buf += 1.0;
                            }
                        }
                        reco_buf *= -1.0;

                    }
                    //1Star
                    else if(rate_buf[0] == 1.0){
                        for(j=2;j<CHECK_PARAMS;j++){
                            if(rate[j] == rate_buf[j]){
                                reco_buf += 5.0;
                            }
                            else if(Math.abs(rate[j] - rate_buf[j]) == 1.0){
                                reco_buf += 1.0;
                            }
                        }
                        reco_buf *= -1.5;

                    }
                }
                reco += reco_buf;


            }
            //Log.v("ID:"+(String)data.get(i).get("id"),Double.toString(reco));
            WriteDBRecommend(Integer.parseInt((String) data.get(i).get("id")), reco);

        }
        mDatabase.close();

    }
    public void WriteDBRate(int id,double rate)
    {
        String dbPath = mDatabasePath.getAbsolutePath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues v = new ContentValues();


        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);

            v.put("rating", rate);


        mDatabase.update("table_recipeLists", v, whereClause, whereArgs);
        mDatabase.close();
    }
    public void WriteDBMemo(int id,String memo)
    {
        String dbPath = mDatabasePath.getAbsolutePath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues v = new ContentValues();


        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);

        v.put("memo", memo);


        mDatabase.update("table_recipeLists", v, whereClause, whereArgs);
        mDatabase.close();
    }
    public void WriteDBRecommend(int id,double recommend)
    {
        String dbPath = mDatabasePath.getAbsolutePath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues v = new ContentValues();


        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        v.put("recommend", recommend);


        mDatabase.update("table_recipeLists", v, whereClause, whereArgs);
        mDatabase.close();
    }



    /**
     *  assetに格納したDBをコピーするため空のDBを作成
     */
    public void createEmptyDataBase() {
        boolean dbExist = checkDataBaseExists();

        if(dbExist) {
            Log.v("test", "createDB あり");
        }else{
            Log.v("test", "createDB DBなし");
            getReadableDatabase();
            try {
                // assets のDBをコピー
                copyDataBaseFormAsset();

                String dbPath = mDatabasePath.getAbsolutePath();
                SQLiteDatabase checkDB = null;

                try {
                    checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                }catch (SQLiteException e) {
                    Log.wtf(TAG, e);
                }

                if (checkDB!=null) {
                    checkDB.setVersion(DATABASE_VERSION);
                    checkDB.close();
                }

            }catch (IOException e) {
                Log.wtf(TAG, e);
            }
        }
    }

    /**
     * assets に格納したDBをデフォルトのDBにコピー
     */
    private void copyDataBaseFormAsset() throws IOException {

        //Log.v("test", "db copy");
        // sample.txt
        /*InputStream is = null;
        BufferedReader br = null;
        try {
            try {
                is = mContext.getAssets().open("sample.txt");
                br = new BufferedReader( new InputStreamReader(is));

                String samp_str;
                String samp_text = "";
                while ((samp_str=br.readLine())!=null) {
                    samp_text += samp_str + "\n";
                }
                Log.v("test", samp_text);
            }finally {
                 if(is != null) is.close();
                if(br != null ) {
                    br.close();
                }
            }
        }catch (Exception e) {
            Log.v("test", "ERROR");
        }*/
        // here.
        //
        // access assets_db
        try {
            InputStream mInput = mContext.getAssets().open(DB_NAME_ORGINAL);

            //if (mInput == null) Log.v("test", "fileが取得できていない");

            // output db
            OutputStream mOutput = new FileOutputStream(mDatabasePath);

            // copy
            byte[] buffer = new byte[1024 * 4];
            int size;
            while ((size = mInput.read(buffer)) > 0) {
                //Log.v("test", "書き込み");
                mOutput.write(buffer, 0, size);
            }
        /*int count = 0;
        int n = 0;
        while (-1 != (n=mInput.read(buffer))) {
            Log.v("test", "かきこみ");
            mOutput.write(buffer, 0, n);
            count += n;
        }*/

            // close
            mOutput.flush();
            mOutput.close();
            mInput.close();
        }catch (Exception e) {
            //error
            Log.e("test", "例外出力", e);
        }
    }

    /**
     * DBがあるかないかを確認
     * @return  存在している場合ture
     */
    private boolean checkDataBaseExists() {
        String dbPath = mDatabasePath.getAbsolutePath();

        SQLiteDatabase checkkDB = null;

        try {
            checkkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e) {
            // db doesn't exit
        }

        if (checkkDB==null) return false;

        int oldVersion = checkkDB.getVersion();
        int newVersion = DATABASE_VERSION;

        if(oldVersion==newVersion) {
            checkkDB.close();
            return true;
        }

        // dbのバージョンが古い場合はDBを削除
        File f = new File(dbPath);
        f.delete();
        return false;
    }

    /*
     * データの読み込み
     *//*
    public Map<Integer,Map> findSuggest(String table){
        Map<Integer, Map> dataList = new HashMap<Integer, Map>();
        String dbPath = mDatabasePath.getAbsolutePath();

        String sql_where = null;
         sql_where = "id=" + String.valueOf(0);

        //try {
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = mDatabase.query(table, null, sql_where, null, null, null,  "recommend DESC",null);

        // 参照先を先頭に
        boolean isEof = cursor.moveToFirst();
        final int ITEMS = 3;
        int i = 0;
        if()
        for(i=0;i<ITEMS;i++) {
            Map<String, String> rowData = new HashMap<String, String>();

            rowData.put("id", cursor.getString(0));

            rowData.put("name", cursor.getString(1));
            rowData.put("rating",cursor.getString(2));
            rowData.put("genre",cursor.getString(3));
            rowData.put("amami",cursor.getString(4));
            rowData.put("shio",cursor.getString(5));
            rowData.put("umami",cursor.getString(6));
            rowData.put("acid",cursor.getString(7));
            rowData.put("pain",cursor.getString(8));
            rowData.put("recommend",cursor.getString(9));
            rowData.put("cooked",cursor.getString(10));



            dataList.put(i, rowData);
            i++;
            cursor.moveToNext();
         }

        cursor.close();
        return dataList;
    }
*/
    public Map<String,String>getKareshi()
    {
        Map<String,String>dataList = new HashMap<String, String>();
        String dbPath = mDatabasePath.getAbsolutePath();
        String sql_where = "id=1";
        mDatabase=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = mDatabase.query("kareshi_data",null,sql_where,null,null,null,"id DESC",null);
        cursor.moveToFirst();
        dataList.put("id",cursor.getString(0));
        dataList.put("name",cursor.getString(1));
        dataList.put("genre",cursor.getString(2));
        dataList.put("menu",cursor.getString(3));
        dataList.put("allergy",cursor.getString(4));
        cursor.close();
        mDatabase.close();
        return dataList;
    }

    public void putKareshi(String column,String data){
        String dbPath = mDatabasePath.getAbsolutePath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues v = new ContentValues();


        String whereClause = "id= ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(1);
        if(column.equals("id"))
        {
            v.put("id", Integer.parseInt(data));
            mDatabase.update("kareshi_data", v, whereClause, whereArgs);
        }
        else if(column.equals("name")){
            v.put("name", data);
            mDatabase.update("kareshi_data", v, whereClause, whereArgs);
        }
        else if(column.equals("genre")){
            v.put("genre",Integer.parseInt(data));
            mDatabase.update("kareshi_data", v, whereClause, whereArgs);
        }
        else if(column.equals("menu")){
            v.put("menu",data);
            mDatabase.update("kareshi_data", v, whereClause, whereArgs);
        }
        else if(column.equals("allergy")){
            v.put("genre",data);
            mDatabase.update("kareshi_data", v, whereClause, whereArgs);
        }

        mDatabase.close();
    }

    public Map<Integer, Map> findAll(String table, int id, int page) {
        Map<Integer, Map> dataList = new HashMap<Integer, Map>();

        //String[] columus = new String[]{"id", "name"};
        String dbPath = mDatabasePath.getAbsolutePath();

        String sql_where = null;
        if(id>0) sql_where = "id=" + String.valueOf(id);

        //try {
            mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = mDatabase.query(table, null, sql_where, null, null, null,  "id DESC",null);

            // 参照先を先頭に
            boolean isEof = cursor.moveToFirst();
            int i = 0;
            while (isEof) {
                Map<String, String> rowData = new HashMap<String, String>();

                rowData.put("id", cursor.getString(0));

                rowData.put("name", cursor.getString(1));
                rowData.put("rating",cursor.getString(2));
                rowData.put("genre",cursor.getString(3));
                rowData.put("amami",cursor.getString(4));
                rowData.put("shio",cursor.getString(5));
                rowData.put("umami",cursor.getString(6));
                rowData.put("acid",cursor.getString(7));
                rowData.put("pain",cursor.getString(8));
                rowData.put("recommend",cursor.getString(9));
                rowData.put("cooked",cursor.getString(10));
                rowData.put("memo",cursor.getString(11));
                rowData.put("ingredients",cursor.getString(12));
                rowData.put("allergy",cursor.getString(13));



                dataList.put(i, rowData);
                i++;

                isEof = cursor.moveToNext();
            }

            cursor.close();
            return dataList;

        //}catch (Exception e) {
            //return Log.e("test", e.toString());
        //}
    }

    public Map<Integer, Map> findSuggest(String table, int id) {
        Map<Integer, Map> dataList = new HashMap<Integer, Map>();

        //String[] columus = new String[]{"id", "name"};
        String dbPath = mDatabasePath.getAbsolutePath();

        String sql_where = null;
        if(id>0) sql_where = "id=" + String.valueOf(id);

        //try {
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = mDatabase.query(table, null, sql_where, null, null, null,  "recommend DESC",Integer.toString(5));

        // 参照先を先頭に
        boolean isEof = cursor.moveToFirst();
        int i = 0;
        while (isEof) {
            Map<String, String> rowData = new HashMap<String, String>();

            rowData.put("id", cursor.getString(0));

            rowData.put("name", cursor.getString(1));
            rowData.put("rating",cursor.getString(2));
            rowData.put("genre",cursor.getString(3));
            rowData.put("amami",cursor.getString(4));
            rowData.put("shio",cursor.getString(5));
            rowData.put("umami",cursor.getString(6));
            rowData.put("acid",cursor.getString(7));
            rowData.put("pain",cursor.getString(8));
            rowData.put("recommend",cursor.getString(9));
            rowData.put("cooked",cursor.getString(10));



            dataList.put(i, rowData);
            i++;

            isEof = cursor.moveToNext();
        }

        cursor.close();
        return dataList;

        //}catch (Exception e) {
        //return Log.e("test", e.toString());
        //}
    }
    public SQLiteDatabase openDataBase() throws SQLException {
        return getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        mDatabase.close();
        super.close();
    }
}
