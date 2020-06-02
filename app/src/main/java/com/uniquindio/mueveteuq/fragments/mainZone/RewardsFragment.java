package com.uniquindio.mueveteuq.fragments.mainZone;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniquindio.mueveteuq.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView cardViewHelado;
    private CardView cardViewPaleta;
    private CardView cardViewGaseosa;
    private CardView cardViewChococake;
    private CardView cardViewEspagueti;
    private CardView cardViewPollo;
    private CardView cardViewGelatina;
    private CardView cardViewLemoncake;
    private CardView cardViewHamburguesa;
    private CardView cardViewPizza;
    private CardView cardViewPapasfritas;
    private CardView cardViewChocomilk;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users;


    public RewardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardsFragment newInstance(String param1, String param2) {
        RewardsFragment fragment = new RewardsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        users = db.collection("Users");

        cardViewHelado = view.findViewById(R.id.cardHeladoCrema);
        cardViewPaleta = view.findViewById(R.id.cardPaleta);
        cardViewGaseosa = view.findViewById(R.id.cardGaseosa);
        cardViewChococake = view.findViewById(R.id.cardChocolateCake);
        cardViewEspagueti = view.findViewById(R.id.cardEspaguetis);
        cardViewPollo = view.findViewById(R.id.cardPolloAsado);
        cardViewGelatina = view.findViewById(R.id.cardGelatina);
        cardViewLemoncake = view.findViewById(R.id.cardTortaLimon);
        cardViewHamburguesa = view.findViewById(R.id.cardHamburguesa);
        cardViewPizza = view.findViewById(R.id.cardPizza);
        cardViewPapasfritas = view.findViewById(R.id.cardPapasFritas);
        cardViewChocomilk = view.findViewById(R.id.cardChocoMilk);


        cardViewHelado.setOnClickListener(this);
        cardViewPaleta.setOnClickListener(this);
        cardViewGaseosa.setOnClickListener(this);
        cardViewChococake.setOnClickListener(this);
        cardViewEspagueti.setOnClickListener(this);
        cardViewPollo.setOnClickListener(this);
        cardViewGelatina.setOnClickListener(this);
        cardViewLemoncake.setOnClickListener(this);
        cardViewHamburguesa.setOnClickListener(this);
        cardViewPizza.setOnClickListener(this);
        cardViewPapasfritas.setOnClickListener(this);
        cardViewChocomilk.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //170 puntos
            case R.id.cardHeladoCrema:
                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
                break;

            //80 puntos
            case R.id.cardPaleta:
                Toast.makeText(getActivity(), "2", Toast.LENGTH_LONG).show();

                break;

            //125 puntos
            case R.id.cardGaseosa:
                Toast.makeText(getActivity(), "3", Toast.LENGTH_LONG).show();

                break;

            //300 puntos
            case R.id.cardChocolateCake:
                Toast.makeText(getActivity(), "4", Toast.LENGTH_LONG).show();

                break;

            //100 puntos
            case R.id.cardEspaguetis:
                break;

            //150 puntos
            case R.id.cardPolloAsado:
                break;

            //60 puntos
            case R.id.cardGelatina:
                break;

            //270 puntos
            case R.id.cardTortaLimon:
                break;

            //240 puntos
            case R.id.cardHamburguesa:
                break;

            //160 puntos
            case R.id.cardPizza:
                break;

            //150 puntos
            case R.id.cardPapasFritas:
                break;

            //125 puntos
            case R.id.cardChocoMilk:
                break;


        }


    }
}
