package com.cookhat.cookbeauty;

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

    /**
     *  assetに格納したDBをコピーするため空のDBを作成
     */
    public void createEmptyDataBase() {
        boolean dbExist = checkDataBaseExists();

        if(dbExist) {
            //Log.v("test", "createDB あり");
        }else{
            //Log.v("test", "createDB DBなし");
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
     */
    public Map<Integer, Map> findAll(String table, int id, int page) {
        Map<Integer, Map> dataList = new HashMap<Integer, Map>();

        //String[] columus = new String[]{"id", "name"};
        String dbPath = mDatabasePath.getAbsolutePath();

        String sql_where = null;
        if(id>0) sql_where = "id=" + String.valueOf(id);

        //try {
            mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            Cursor cursor = mDatabase.query(table, null, sql_where, null, null, null, "default_para DESC, id DESC");

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
