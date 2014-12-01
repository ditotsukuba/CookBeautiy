package com.cookhat.cookbeauty;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class SettingFragment extends Fragment {
    private  DBHelper mDbHelper;
    private  PopupWindow mPopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        // ボタンを取得して、ClickListenerをセット
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        Button button_r = (Button)getActivity().findViewById(R.id.button_reset);
        Button button_v = (Button)getActivity().findViewById(R.id.button_ver);

        button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = getActivity().getLayoutInflater().inflate(R.layout.setting_popup,null);
                Button button_y = (Button)popup.findViewById(R.id.popup_yes);
                Button button_n = (Button)popup.findViewById(R.id.popup_no);
                final PopupWindow popupWindow = new PopupWindow(getActivity());
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(popup);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

                button_y.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Toast.makeText(getActivity(), "リセットしました", Toast.LENGTH_SHORT).show();
                                 popupWindow.dismiss();

                             }
                });
                button_n.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }

        });
button_v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        View popup = getActivity().getLayoutInflater().inflate(R.layout.ver_popup,null);
        Button button_y2 = (Button)popup.findViewById(R.id.popup_yes2);
        final PopupWindow popupWindow = new PopupWindow(getActivity());
        popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popup);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);

    button_y2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
        }
    });
    }
});

    }

}


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
/**
public class SettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
  /**
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SettingFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

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
  /**
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
*/