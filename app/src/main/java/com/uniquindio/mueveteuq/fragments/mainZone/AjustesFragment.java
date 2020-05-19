package com.uniquindio.mueveteuq.fragments.mainZone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uniquindio.mueveteuq.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AjustesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjustesFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView nicknameTv;
    TextView nicknameDescriptionTv;
    TextView emailTv;
    TextView emailDescriptionTv;
    TextView passwordTv;
    TextView passwordDescriptionTv;

    public AjustesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AjustesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjustesFragment newInstance(String param1, String param2) {
        AjustesFragment fragment = new AjustesFragment();
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
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        nicknameTv = view.findViewById(R.id.title_nickname);
        nicknameDescriptionTv = view.findViewById(R.id.title_nickname_description);
        emailTv = view.findViewById(R.id.title_email);
        emailDescriptionTv = view.findViewById(R.id.title_email_description);
        passwordTv = view.findViewById(R.id.title_password);
        passwordDescriptionTv = view.findViewById(R.id.title_password_description);


        nicknameTv.setOnClickListener(this);
        nicknameDescriptionTv.setOnClickListener(this);
        emailTv.setOnClickListener(this);
        emailDescriptionTv.setOnClickListener(this);
        passwordTv.setOnClickListener(this);
        passwordDescriptionTv.setOnClickListener(this);

        return view;


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.title_nickname_description || id == R.id.title_nickname){

        }
        else if(id == R.id.title_email || id == R.id.title_email_description){

        }

        else if(id == R.id.title_password || id == R.id.title_password_description){


        }

    }
}
