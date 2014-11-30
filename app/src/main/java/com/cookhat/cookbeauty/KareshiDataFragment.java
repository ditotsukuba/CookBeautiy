package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.Inflater;


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


        return inflater.inflate(R.layout.activity_kareshi_edit, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        final Button button = (Button)getActivity().findViewById(R.id.allergyButton);
        final Button accept_button = (Button)getActivity().findViewById(R.id.allergy_accept_button);
        final Button cancel_button = (Button)getActivity().findViewById(R.id.allergy_cancel_button);

        //　allelgy.txtに彼のアレルギー情報を保持
        //　空ファイルなら"なし"　データがあれば取得してボタンのテキストを変更する
        String his_allergy = null;
        try{
            AssetManager asset = getResources().getAssets();
            InputStream in = asset.open("allergy.txt");

            BufferedReader reader =new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String s;


            while ((s = reader.readLine()) != null) {
                his_allergy = his_allergy + s.toString() + ",";
            }

            if(his_allergy == null)
                button.setText("なし");
            else
                button.setText(his_allergy);

        }catch(IOException e){
            e.printStackTrace();
        }

        // allergyList.txtにアレルギー主品目を保持
        // ボタンが押されたとき　ポップアップで主品目一覧　を表示する
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                // allergyList.txt から his_allergyにデータ格納
                ArrayList<String> his_allergy_list = new ArrayList<String>();
                try{
                    AssetManager asset = getResources().getAssets();
                    InputStream in = asset.open("allergyList.txt");

                    BufferedReader reader =new BufferedReader(new InputStreamReader(in,"UTF-8"));
                    String s;

                    while ((s = reader.readLine()) != null) {
                        his_allergy_list.add(s.toString());
                    }

                }catch(IOException e){
                    e.printStackTrace();
                }
                */
                PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(getActivity().getLayoutInflater().inflate(R.layout.popup_allergy_list, null));
                //popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

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


                //mPopupWindow = new PopupWindow();
                //mPopupWindow.isShowing();


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
