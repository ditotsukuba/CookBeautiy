package com.cookhat.cookbeauty;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;


public class RecipeFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private  DBHelper mDbHelper;
    private SQLiteDatabase db;
    private int load_id = 0;
    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Integer id = getArguments().getInt("id");
        load_id = id;
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);
        mDbHelper = new DBHelper(getActivity());
        db = mDbHelper.getReadableDatabase();

        Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", id, 0);
        Map<String, String> rowData;
        //if(columns.size()==1){
            rowData = columns.get(0);
            TextView titleView = (TextView) v.findViewById(R.id.recipe_name);
            titleView.setText(rowData.get("name"));
            //Log.v("test", rowData.get("name"));
        //}


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
                db = mDbHelper.getReadableDatabase();
                Map<Integer, Map> columns = mDbHelper.findAll("table_recipeLists", 0, 0);
                Iterator iterator = columns.keySet().iterator();
                while (iterator.hasNext()) {
                    Object o = iterator.next();
                    //rowData = columus.get(o);
                    String name_buf = (String)columns.get(o).get("name");
                    int key = Integer.parseInt((String) columns.get(o).get("id"));
                    Log.v("料理名", name_buf);
                    Log.v("オススメ度", (String) columns.get(o).get("recommend"));
                }
            }
        });

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

}
