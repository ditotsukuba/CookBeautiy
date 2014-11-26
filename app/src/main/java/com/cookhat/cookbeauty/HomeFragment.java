package com.cookhat.cookbeauty;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View v = inflater.inflate(R.layout.fragment_home_fragement,container,false);
        return inflater.inflate(R.layout.fragment_home_fragement, container, false);
    }


}
