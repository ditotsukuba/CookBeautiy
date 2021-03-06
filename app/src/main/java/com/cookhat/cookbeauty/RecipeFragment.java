package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;


public class RecipeFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private FrameActivity frame;
    private  DBHelper mDbHelper;
    private SQLiteDatabase db;
    private int load_id = 0;
    private int tap_button = 0;//0:Not button tap,1:button taped

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frame.changeState(4);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        Integer id = getArguments().getInt("id");
        load_id = id;
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        ImageView allergy = (ImageView)v.findViewById(R.id.allergy_view);
        allergy.setVisibility(View.INVISIBLE);


        //DB関連処理
        mDbHelper = new DBHelper(getActivity());
        db = mDbHelper.getReadableDatabase();


        Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", id, 0);
        final Map<String, String> rowData;
        //if(columns.size()==1){
            rowData = columns.get(0);
            TextView titleView = (TextView) v.findViewById(R.id.recipe_name);
            titleView.setText(rowData.get("name"));
            final TextView memoText = (TextView)v.findViewById(R.id.memo_text);
            memoText.setText(rowData.get("memo"));
            if(rowData.get("memo").equals("タップして入力してください")) {
                memoText.setTextColor(Color.parseColor("#808080"));
            }
            else{
                memoText.setTextColor(Color.parseColor("#000000"));
            }

            //Log.v("test", rowData.get("name"));
        //}
        //アレルギーが現れた！！
        String allergy_db[] = rowData.get("allergy").split(",");
        String allergy_kareshi[]=mDbHelper.getKareshi().get("allergy").split(",");
        if(!(allergy_kareshi[0].equals("なし"))) {
            for (int i = 0; i < allergy_db.length; i++) {
                for (int j = 0; j < allergy_kareshi.length; j++) {
                    if (allergy_db[i].equals(allergy_kareshi[j])) {
                        allergy.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        //RatingBar関連処理
        RatingBar ratingbar = (RatingBar)v.findViewById(R.id.ratingBar);
        ratingbar.setRating(Float.parseFloat(rowData.get("rating")));
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
           @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                //テストコード
               // Toast.makeText(getActivity(), "New Rating: " + rating, Toast.LENGTH_SHORT).show();
               mDbHelper.WriteDBRate(load_id,rating);
               mDbHelper.calcRecommend();

               //テストコード
               // db = mDbHelper.getReadableDatabase();

                Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", 0, 0);
                Iterator iterator = columns.keySet().iterator();
                while (iterator.hasNext()) {
                    Object o = iterator.next();
                    //rowData = columus.get(o);
                    String name_buf = (String)columns.get(o).get("name");
                    int key = Integer.parseInt((String) columns.get(o).get("id"));
                   // Log.v("料理名", name_buf);
                    //Log.v("オススメ度", (String) columns.get(o).get("recommend"));
                }
            }
        });

        //材料関連処理


        TextView ing = (TextView)v.findViewById(R.id.ingredients);
        ing.setText(rowData.get("ingredients"));
        ing.setTextColor(Color.parseColor("#000000"));
        //DialogFragmentでやってみる


        //Memo機能関連処理

        final ImageView memo = (ImageView)v.findViewById(R.id.memo_window);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tap_button = 0;

                final View popup = getActivity().getLayoutInflater().inflate(R.layout.input_popup, null);
                final PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(popup);
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(popup, Gravity.CENTER_VERTICAL, 0, 0);
                InputMethodManager im;
                RelativeLayout rl;
                im =(InputMethodManager) frame.getSystemService(Context.INPUT_METHOD_SERVICE);
                rl = (RelativeLayout)popup.findViewById(R.id.inputpopupwindow);
                final EditText memo_data = (EditText)popup.findViewById(R.id.inputtextbox);
                String m = mDbHelper.ReadDBMemo(load_id);
                if(!m.equals("タップして入力してください")) {
                    memo_data.setText(m);
                }
                final Button ok_button = (Button)popup.findViewById(R.id.editButton);
                final Button cancel_button = (Button)popup.findViewById(R.id.editButton_c);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (tap_button == 0) {
                            String data = memo_data.getText().toString();
                            if (data.equals("")) {
                                data = "タップして入力してください";
                            }
                            mDbHelper.WriteDBMemo(load_id, data);
                            if (!data.equals("タップして入力してください")) {
                                memoText.setText(data);
                                memoText.setTextColor(Color.parseColor("#000000"));
                            } else {
                                memoText.setText("タップして入力してください");
                                memoText.setTextColor(Color.parseColor("#808080"));
                            }
                        }
                    }
                });


                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String data = memo_data.getText().toString();
                        if(data.equals(""))
                        {
                           data = "タップして入力してください" ;
                        }
                        mDbHelper.WriteDBMemo(load_id,data);
                        if(!data.equals("タップして入力してください")) {
                            memoText.setText(data);
                            memoText.setTextColor(Color.parseColor("#000000"));
                        }
                        else{
                            memoText.setText("タップして入力してください");
                            memoText.setTextColor(Color.parseColor("#808080"));
                        }

                        tap_button = 1;
                        popupWindow.dismiss();
                    }
                });

                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tap_button = 1;
                        popupWindow.dismiss();
                    }
                });

            }
        });
        //db.close();
        mDbHelper.close();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
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
    */

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onAttach(Activity activity) {
        frame = (FrameActivity) activity;
        super.onAttach(activity);
    }

}
