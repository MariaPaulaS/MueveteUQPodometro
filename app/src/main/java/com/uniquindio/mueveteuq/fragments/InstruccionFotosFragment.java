package com.uniquindio.mueveteuq.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.uniquindio.mueveteuq.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mueveteuq_podometro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstruccionFotosFragment extends Fragment {


    public InstruccionFotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruccion_fotos, container, false);
    }

}
