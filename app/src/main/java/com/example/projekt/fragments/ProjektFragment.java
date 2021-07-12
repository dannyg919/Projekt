package com.example.projekt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projekt.R;

public class ProjektFragment extends Fragment {

    public ProjektFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_projekt, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}
