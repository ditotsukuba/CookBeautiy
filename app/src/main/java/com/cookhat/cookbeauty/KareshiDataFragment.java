package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.util.Xml;

public class KareshiDataFragment extends Fragment {

        private DBHelper mDBHelper;
    private  Map<String,String> kareshi_database;
    private CheckBox allergy_checkbox[];
    private Button allergy_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View v =inflater.inflate(R.layout.activity_kareshi_edit, container, false);

        allergy_button = (Button) v.findViewById(R.id.allergyButton);

        mDBHelper = new DBHelper(getActivity());
        mDBHelper.createEmptyDataBase();

        kareshi_database =  mDBHelper.getKareshi();
/*
        // 好きなジャンルの設定
        String genre = kareshi_database.get("genre");
        if(genre.equals("和")){
            RadioButton genre_checkbutton = (RadioButton) getActivity().findViewById(R.id.japanese);
            genre_checkbutton.setChecked(true);
        }else if(genre.equals("洋")){
            RadioButton genre_checkbutton = (RadioButton) getActivity().findViewById(R.id.europe);
            genre_checkbutton.setChecked(true);
        }else if(genre.equals("中")){
            RadioButton genre_checkbutton = (RadioButton) getActivity().findViewById(R.id.chinese);
            genre_checkbutton.setChecked(true);
        }
*/


        // allergy がなければ "なし" を表示
        // あれば カンマで区切って表示
        if (kareshi_database.get("allergy") == null)
            allergy_button.setText("なし");
        else {
            String allergy_text = kareshi_database.get("allergy");
            String replace_text =allergy_text.replaceAll("\n", ", ");
            allergy_button.setText(replace_text);
        }


        allergy_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {





                //ポップアップの処理
                View popup = getActivity().getLayoutInflater().inflate(R.layout.popup_allergy_list, null);
                final PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(popup);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
                final Button accept_button = (Button) popup.findViewById(R.id.allergy_accept_button);
                final Button cancel_button = (Button) popup.findViewById(R.id.allergy_cancel_button);

                // allergyList.txt から allergy_list にデータ格納
                final ArrayList<String> allergy_list = new ArrayList<String>();
                try{
                    AssetManager asset = getResources().getAssets();
                    InputStream in = asset.open("allergyList.txt");

                    BufferedReader reader =new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    String s;

                    while ((s = reader.readLine()) != null) {
                        allergy_list.add(s.toString());
                    }

                    in.close();
                }catch(IOException e){
                    e.printStackTrace();
                }

                //チェックボタンのidを map型 で格納
                Map<Integer,String> dataMap = new HashMap<Integer, String>();
                for(int i=0;i<allergy_list.size();i++)
                {
                    dataMap.put(i,allergy_list.get(i));
                }
                final Map<String,CheckBox>allergyCheckButtonList = new HashMap<String, CheckBox>();
                allergyCheckButtonList.put(allergy_list.get(0), (CheckBox)popup.findViewById(R.id.allergy_ebi));
                allergyCheckButtonList.put(allergy_list.get(1), (CheckBox)popup.findViewById(R.id.allergy_kani));
                allergyCheckButtonList.put(allergy_list.get(2), (CheckBox)popup.findViewById(R.id.allergy_komugi));
                allergyCheckButtonList.put(allergy_list.get(3), (CheckBox)popup.findViewById(R.id.allergy_soba));
                allergyCheckButtonList.put(allergy_list.get(4), (CheckBox)popup.findViewById(R.id.allergy_tamago));
                allergyCheckButtonList.put(allergy_list.get(5), (CheckBox)popup.findViewById(R.id.allergy_titi));
                allergyCheckButtonList.put(allergy_list.get(6), (CheckBox)popup.findViewById(R.id.allergy_rakkasei));
                allergyCheckButtonList.put(allergy_list.get(7), (CheckBox)popup.findViewById(R.id.allergy_awabi));
                allergyCheckButtonList.put(allergy_list.get(8), (CheckBox)popup.findViewById(R.id.allergy_ika));
                allergyCheckButtonList.put(allergy_list.get(9), (CheckBox)popup.findViewById(R.id.allergy_ikura));
                allergyCheckButtonList.put(allergy_list.get(10), (CheckBox)popup.findViewById(R.id.allergy_sake));
                allergyCheckButtonList.put(allergy_list.get(11), (CheckBox)popup.findViewById(R.id.allergy_saba));
                allergyCheckButtonList.put(allergy_list.get(12), (CheckBox)popup.findViewById(R.id.allergy_kasyunattu));
                allergyCheckButtonList.put(allergy_list.get(13), (CheckBox)popup.findViewById(R.id.allergy_kurumi));
                allergyCheckButtonList.put(allergy_list.get(14), (CheckBox)popup.findViewById(R.id.allergy_daizu));
                allergyCheckButtonList.put(allergy_list.get(15), (CheckBox)popup.findViewById(R.id.allergy_goma));
                allergyCheckButtonList.put(allergy_list.get(16), (CheckBox)popup.findViewById(R.id.allergy_matutake));
                allergyCheckButtonList.put(allergy_list.get(17), (CheckBox)popup.findViewById(R.id.allergy_yamaimo));
                allergyCheckButtonList.put(allergy_list.get(18), (CheckBox)popup.findViewById(R.id.allergy_orenzi));
                allergyCheckButtonList.put(allergy_list.get(19), (CheckBox)popup.findViewById(R.id.allergy_kiui));
                allergyCheckButtonList.put(allergy_list.get(20), (CheckBox)popup.findViewById(R.id.allergy_banana));
                allergyCheckButtonList.put(allergy_list.get(21), (CheckBox)popup.findViewById(R.id.allergy_ringo));
                allergyCheckButtonList.put(allergy_list.get(22), (CheckBox)popup.findViewById(R.id.allergy_momo));
                allergyCheckButtonList.put(allergy_list.get(23), (CheckBox)popup.findViewById(R.id.allergy_zeratin));
                allergyCheckButtonList.put(allergy_list.get(24), (CheckBox)popup.findViewById(R.id.allergy_gyuuniku));
                allergyCheckButtonList.put(allergy_list.get(25), (CheckBox)popup.findViewById(R.id.allergy_butaniku));
                allergyCheckButtonList.put(allergy_list.get(26), (CheckBox)popup.findViewById(R.id.allergy_toriniku));

                allergy_checkbox = new CheckBox[allergy_list.size()];
                String allergy_text[] = kareshi_database.get("allergy").split(",");


                    for(int j=0; j<allergy_text.length;j++)
                    {
                        if(allergy_text[j].equals("なし")) {

                        }
                        else
                        {
                            CheckBox c = allergyCheckButtonList.get(allergy_text[j]);
                            c.setChecked(true);
                        }
                    }





                accept_button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        String tempstr = "";

                            for (int j=0; j<allergy_list.size(); j++) {
                                String syokuhin = allergy_list.get(j);
                                CheckBox c = allergyCheckButtonList.get(syokuhin);

                                if(c.isChecked()) {  // チェックされているとき
                                    tempstr = tempstr + syokuhin + ",";

                                }
                            }


                        if(tempstr.equals(""))
                        {
                            tempstr = "なし";
                        }

                        mDBHelper.putKareshi("allergy", tempstr);


                        kareshi_database =  mDBHelper.getKareshi();
                        String allergy_text[] = kareshi_database.get("allergy").split(",");
                        String Button_output = new String();
                        for(int j=0; j<allergy_text.length;j++)
                        {
                            if(j == (allergy_text.length -1))
                            {
                                Button_output += allergy_text[j];
                            }
                            else
                            {
                                Button_output += allergy_text[j] + ",";
                            }
                        }
                        allergy_button.setText(Button_output);
                        popupWindow.dismiss();



                    }
                });

                cancel_button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //Log.v("test", "cancel");
                        //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();
                    }
                });

            }
        });

        //好きなジャンルを選択したらデータベースに格納
        RadioButton genre_japanese = (RadioButton)v.findViewById(R.id.japanese);
        RadioButton genre_europe = (RadioButton)v.findViewById(R.id.europe);
        RadioButton genre_chinese = (RadioButton)v.findViewById(R.id.chinese);
        String genre = kareshi_database.get("genre");
        if(genre.equals("0")){
            Log.v("test","wa");
            genre_japanese.setChecked(true);
            genre_chinese.setChecked(false);
            genre_europe.setChecked(false);

        }else if(genre.equals("1")){
            genre_japanese.setChecked(false);
            genre_chinese.setChecked(false);
            genre_europe.setChecked(true);
        }else if(genre.equals("2")){

            genre_japanese.setChecked(false);
            genre_chinese.setChecked(true);
            genre_europe.setChecked(false);
        }
        genre_japanese.setOnClickListener(new japaneseClickListener());

        genre_europe.setOnClickListener(new europeClickListener());

        genre_chinese.setOnClickListener(new chineseClickListener());
        //彼氏の名前を入力したらデータベースに格納
        final EditText boyfriend_name = (EditText)v.findViewById(R.id.edit_name);
        String name = kareshi_database.get("name");
        boyfriend_name.setText(name);
        boyfriend_name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //キーボード表示を制御するためのオブジェクト
                InputMethodManager inputMethodManager =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if (event.getAction() == KeyEvent.ACTION_DOWN  && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //Toast.makeText(getActivity(), boyfriend_name.getText().toString(), Toast.LENGTH_SHORT).show();
                    //エンターキーでキーボードを非表示
                    inputMethodManager.hideSoftInputFromWindow(boyfriend_name.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    DBHelper mDBHelper = new DBHelper(getActivity());
                    mDBHelper.putKareshi("name",boyfriend_name.getText().toString());

                    return true;
                }

                return false;
            }
        });
        boyfriend_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                // フォーカスを受け取ったとき
                if (hasFocus) {
                    // ソフトキーボードを表示する
                    inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                }
                // フォーカスが外れたとき
                else {
                    // ソフトキーボードを閉じる
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(getActivity(), "エンターで確定してください", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    public class japaneseClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            DBHelper mDBHelper = new DBHelper(getActivity());

            mDBHelper.putKareshi("genre","0");
            Map<String,String> data = mDBHelper.getKareshi();
            Log.v("ジャンル",data.get("genre"));


            //Toast.makeText(getActivity(), "和", Toast.LENGTH_SHORT).show();
        }
    }
    public class europeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            DBHelper mDBHelper = new DBHelper(getActivity());
            mDBHelper.putKareshi("genre","1");
            Map<String,String> data = mDBHelper.getKareshi();
            Log.v("ジャンル",data.get("genre"));
            //Toast.makeText(getActivity(), "洋", Toast.LENGTH_SHORT).show();
        }
    }
    public class chineseClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            DBHelper mDBHelper = new DBHelper(getActivity());
            mDBHelper.putKareshi("genre","2");
            Map<String,String> data = mDBHelper.getKareshi();
            Log.v("ジャンル",data.get("genre"));
            //Toast.makeText(getActivity(), "中", Toast.LENGTH_SHORT).show();
        }
    }


/*
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.main);

            EditText editText = (EditText)findViewById(R.id.edit_name);
            // テキストが変化した際のリスナーをセット
            editText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //変更前
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            //変更直前
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //変更後
            //Toast.makeText(getActivity(), editable.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause(){
            EditText editText = (EditText)findViewById(R.id.edit_name);
            Toast.makeText(getActivity(), editText.toString(), Toast.LENGTH_SHORT).show();
        }
*/

/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
*/


}
