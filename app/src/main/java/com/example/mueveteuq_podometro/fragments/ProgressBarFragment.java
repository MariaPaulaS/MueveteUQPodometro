package com.example.mueveteuq_podometro.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mueveteuq_podometro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressBarFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvProcesando;

    public ProgressBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_progress_bar, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        tvProcesando = view.findViewById(R.id.tv_procesando);

        progressBar.setVisibility(View.VISIBLE);

        return view;
    }

}
