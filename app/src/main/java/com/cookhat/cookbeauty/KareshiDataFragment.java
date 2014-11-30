package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;
import android.view.View.OnClickListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.util.Xml;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KareshiDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KareshiDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class KareshiDataFragment extends Fragment {

    private PopupWindow mPopupWindow;



    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
*/
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KareshiDataFragment.
     */
/*    // TODO: Rename and change types and number of parameters
    public static KareshiDataFragment newInstance(String param1, String param2) {
        KareshiDataFragment fragment = new KareshiDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public KareshiDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View v =inflater.inflate(R.layout.activity_kareshi_edit, container, false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Button allergy_button = (Button) getActivity().findViewById(R.id.allergyButton);

        //　allelgy.txtに彼のアレルギー情報を保持
        //　空ファイルなら"なし"　データがあれば取得してボタンのテキストを変更する
        final ArrayList<String> his_allergy = new ArrayList<String>();
        final ArrayList<String> not_his_allergy = new ArrayList<String>();
        try {
            AssetManager asset = getResources().getAssets();
            InputStream in = asset.open("allergy.txt");

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;


            while ((s = reader.readLine()) != null) {
                String[] temp = s.toString().split(",");
                if (Integer.parseInt(temp[1]) == 1)    // 1 がアレルギー（0は何もない）
                    his_allergy.add(temp[0]);
                else
                    not_his_allergy.add(temp[0]);
            }

            if (his_allergy.isEmpty())
                allergy_button.setText("なし");
            else {
                String temp_string = "";
                for(int i=0; i < his_allergy.size(); i++)
                    temp_string = temp_string + his_allergy.get(i) + " ";
                allergy_button.setText(temp_string);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // allergyList.txtにアレルギー主品目を保持
        // ボタンが押されたとき ポップアップで主品目一覧 を表示する
        allergy_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // allergyList.txt から his_allergyにデータ格納
                //Map<String,Integer> map = new HashMap<String,Integer>();
                /*
                ArrayList<String> allergy_list = new ArrayList<String>();

                try{
                    AssetManager asset = getResources().getAssets();
                    InputStream in = asset.open("allergyList.txt");

                    BufferedReader reader =new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    String s;

                    while ((s = reader.readLine()) != null) {
                        allergy_list.add(s.toString());
                    }

                }catch(IOException e){
                    e.printStackTrace();
                }
*/

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
                final CheckBox ebi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_ebi);
                final CheckBox kani_checkbox = (CheckBox) popup.findViewById(R.id.allergy_kani);
                final CheckBox komugi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_komugi);
                final CheckBox soba_checkbox = (CheckBox) popup.findViewById(R.id.allergy_soba);
                final CheckBox tamago_checkbox = (CheckBox) popup.findViewById(R.id.allergy_tamago);
                final CheckBox titi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_titi);
                final CheckBox rakkasei_checkbox = (CheckBox) popup.findViewById(R.id.allergy_rakkasei);
                final CheckBox awabi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_awabi);
                final CheckBox ika_checkbox = (CheckBox) popup.findViewById(R.id.allergy_ika);
                final CheckBox ikura_checkbox = (CheckBox) popup.findViewById(R.id.allergy_ikura);
                final CheckBox sake_checkbox = (CheckBox) popup.findViewById(R.id.allergy_sake);
                final CheckBox saba_checkbox = (CheckBox) popup.findViewById(R.id.allergy_saba);
                final CheckBox kasyunattu_checkbox = (CheckBox) popup.findViewById(R.id.allergy_kasyunattu);
                final CheckBox kurumi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_kurumi);
                final CheckBox daizu_checkbox = (CheckBox) popup.findViewById(R.id.allergy_daizu);
                final CheckBox goma_checkbox = (CheckBox) popup.findViewById(R.id.allergy_goma);
                final CheckBox matutake_checkbox = (CheckBox) popup.findViewById(R.id.allergy_matutake);
                final CheckBox yamaimo_checkbox = (CheckBox) popup.findViewById(R.id.allergy_yamaimo);
                final CheckBox orenzi_checkbox = (CheckBox) popup.findViewById(R.id.allergy_orenzi);
                final CheckBox kiui_checkbox = (CheckBox) popup.findViewById(R.id.allergy_kiui);
                final CheckBox banana_checkbox = (CheckBox) popup.findViewById(R.id.allergy_banana);
                final CheckBox ringo_checkbox = (CheckBox) popup.findViewById(R.id.allergy_ringo);
                final CheckBox momo_checkbox = (CheckBox) popup.findViewById(R.id.allergy_momo);
                final CheckBox zeratin_checkbox = (CheckBox) popup.findViewById(R.id.allergy_zeratin);
                final CheckBox gyuuniku_checkbox = (CheckBox) popup.findViewById(R.id.allergy_gyuuniku);
                final CheckBox butaniku_checkbox = (CheckBox) popup.findViewById(R.id.allergy_butaniku);
                final CheckBox toriniku_checkbox = (CheckBox) popup.findViewById(R.id.allergy_toriniku);

                for(int i=0; i < his_allergy.size(); i++){
                    String allergy = his_allergy.get(i);
                    if(allergy == "えび")
                        ebi_checkbox.setChecked(true);
                        //getActivity().setContentView(ebi_checkbox);
                    if(allergy == "かに")
                        kani_checkbox.setChecked(true);
                    if(allergy == "小麦")
                        komugi_checkbox.setChecked(true);
                    if(allergy == "そば")
                        soba_checkbox.setChecked(true);
                        //getActivity().setContentView(soba_checkbox);
                    if(allergy == "卵")
                        tamago_checkbox.setChecked(true);
                    if(allergy == "乳")
                        titi_checkbox.setChecked(true);
                    if(allergy == "落花生")
                        rakkasei_checkbox.setChecked(true);
                    if(allergy == "あわび")
                        awabi_checkbox.setChecked(true);
                    if(allergy == "いか")
                        ika_checkbox.setChecked(true);
                    if(allergy == "いくら")
                        ikura_checkbox.setChecked(true);
                    if(allergy == "サケ")
                        sake_checkbox.setChecked(true);
                    if(allergy == "サバ")
                        saba_checkbox.setChecked(true);
                    if(allergy == "カシューナッツ")
                        kasyunattu_checkbox.setChecked(true);
                    if(allergy == "くるみ")
                        kurumi_checkbox.setChecked(true);
                    if(allergy == "大豆")
                        daizu_checkbox.setChecked(true);
                    if(allergy == "ゴマ")
                        goma_checkbox.setChecked(true);
                    if(allergy == "松茸")
                        matutake_checkbox.setChecked(true);
                    if(allergy == "山芋")
                        yamaimo_checkbox.setChecked(true);
                    if(allergy == "オレンジ")
                        orenzi_checkbox.setChecked(true);
                    if(allergy == "キウイフルーツ")
                        kiui_checkbox.setChecked(true);
                    if(allergy == "バナナ")
                        banana_checkbox.setChecked(true);
                    if(allergy == "りんご")
                        ringo_checkbox.setChecked(true);
                    if(allergy == "桃")
                        momo_checkbox.setChecked(true);
                    if(allergy == "ゼラチン")
                        zeratin_checkbox.setChecked(true);
                    if(allergy == "牛肉")
                        gyuuniku_checkbox.setChecked(true);
                    if(allergy == "豚肉")
                        butaniku_checkbox.setChecked(true);
                    if(allergy == "鶏肉")
                        toriniku_checkbox.setChecked(true);

                }

/*
                InputStream is = null;
                try {
                    is = getActivity().openFileInput("res/layout/popup_allergy_list.xml");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //InputStream is = (InputStream) getResources().getLayout(R.layout.popup_allergy_list);

                XmlPullParser xpp = Xml.newPullParser();
                try {
                    xpp.setInput(is, "UTF-8");
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                int eventType = 0;
                try {
                    eventType = xpp.getEventType();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("checkbox"))
                            Log.v("start tag", xpp.getName());
                    } else if(eventType == XmlPullParser.TEXT) {
                        Log.v("text", xpp.getName());
                    }

                    try {
                        eventType = xpp.next();//increment
                        Log.v("test", xpp.getName());
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
*/

/*
                InputStream is = getResources()
                        .openRawResource(R.layout.popup_allergy_list);

                Persister persister = new Persister();
                Book book = null;
                try {
                    // 読み込む
                    book = persister.read(Book.class, is);
                } catch (Exception e) {
                }
*/
                /*
                // his_allergy の個数だけチェックボックス生成
                CheckBox[] allergy_button = new CheckBox[his_allergy_list.size()];
                //getActivity().findViewById(R.layout.popup_allergy_list);

                //画面サイズを取得してLeyoutのサイズ決定(1/2サイズ)
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width = (int)display.getWidth();
                int height = (int)display.getHeight();
                width = (int)(width/Math.sqrt(2.0));
                height = (int)(height/Math.sqrt(2.0));

                //画面外にはみ出るのでScrollViewに
                ScrollView scrollView = new ScrollView(getActivity().getApplicationContext());
                getActivity().setContentView(scrollView);

                LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                layout.setLayoutParams(params);
                //layout.setBackgroundColor(getResources().getColor(R.color.black));
                layout.setOrientation(LinearLayout.VERTICAL);
                scrollView.addView(layout);


                for (int i = 0; i < his_allergy_list.size(); i++){
                    //allergy_button[i].setId( i+1000 );    // リソースID設定 ( ******適当です********　←　ここ重要！！)


                    allergy_button[i] = new CheckBox(getActivity().getApplicationContext());
                    allergy_button[i].setText(his_allergy_list.get(i));    //ボタンテキスト設定


                    // レイアウトにチェックボックスを追加
                    layout.addView(allergy_button[i], new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));


                }
                //getActivity().setContentView(scrollView);
                //getActivity().setContentView(layout);


                //Toast.makeText(getActivity(), "hoge!", Toast.LENGTH_SHORT).show();
                mPopupWindow = new PopupWindow();
                View popupView = getLayoutInflater().inflate(, null);


                PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);*/
/*
                PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(getActivity().getLayoutInflater().inflate(R.layout.popup_allergy_list, null));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
*/


                accept_button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // Write
                        {
                            try {
                                String file_data="";
                                for(int i=0; i<his_allergy.size();i++)
                                    file_data = file_data + ",1\n";
                                for(int i=0; i<not_his_allergy.size();i++)
                                    file_data = file_data + ",0\n";
                                getActivity().deleteFile("allergy.txt");
                                FileOutputStream fileOutputStream = getActivity().openFileOutput("allergy.txt", Context.MODE_PRIVATE);
                                fileOutputStream.write( file_data.getBytes() );

                                fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                            } catch (IOException e) {
                            }
                            Toast.makeText(getActivity(), "変更されました？", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        }

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




    }
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
