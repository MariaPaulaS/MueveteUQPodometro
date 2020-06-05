package com.uniquindio.mueveteuq.fragments.mainZone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.codesgood.views.JustifiedTextView;
import com.uniquindio.mueveteuq.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView titleConsejo;
    TextView descriptionConsejo;

    public TipsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipsFragment newInstance(String param1, String param2) {
        TipsFragment fragment = new TipsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        titleConsejo = view.findViewById(R.id.tv_titulo_consejo);
        descriptionConsejo = view.findViewById(R.id.tv_descripcion_consejo);


        generarConsejo();

        return view;
    }


    public void generarConsejo(){

        int num = (int) (Math.random() * 6 + 1);


        switch (num){

            case 0:
                titleConsejo.setText("0");
                break;

            case 1:
                titleConsejo.setText(R.string.tip_1_title);
                descriptionConsejo.setText(R.string.tip_1);
                break;

            case 2:
                titleConsejo.setText(R.string.tip_2_title);
                descriptionConsejo.setText(R.string.tip_2);
                break;


            case 3:
                titleConsejo.setText(R.string.tip_3_title);
                descriptionConsejo.setText(R.string.tip_3);
                break;


            case 4:
                titleConsejo.setText(R.string.tip_4_title);
                descriptionConsejo.setText(R.string.tip_4);

                break;

            case 5:
                titleConsejo.setText(R.string.tip_5_title);
                descriptionConsejo.setText(R.string.tip_5);
                break;

            default:
                titleConsejo.setText(R.string.tip_0_title);
                descriptionConsejo.setText(R.string.tip_0);
                break;

        }

    }
}