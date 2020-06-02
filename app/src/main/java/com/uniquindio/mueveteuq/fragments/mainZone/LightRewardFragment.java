package com.uniquindio.mueveteuq.fragments.mainZone;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uniquindio.mueveteuq.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LightRewardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightRewardFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView cardViewManzana;
    private CardView cardViewMandarina;
    private CardView cardViewNaranja;
    private CardView cardViewTomate;
    private CardView cardViewMelon;
    private CardView cardViewSandia;
    private CardView cardViewUvas;
    private CardView cardViewHuevo;
    private CardView cardViewBanano;
    private CardView cardViewCafe;
    private CardView cardViewJugo;
    private CardView cardViewAguaGas;

    public LightRewardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LightRewardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LightRewardFragment newInstance(String param1, String param2) {
        LightRewardFragment fragment = new LightRewardFragment();
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
        View view = inflater.inflate(R.layout.fragment_light_reward, container, false);


        cardViewManzana = view.findViewById(R.id.cardManzana);
        cardViewMandarina = view.findViewById(R.id.cardMandarina);
        cardViewNaranja = view.findViewById(R.id.cardNaranja);
        cardViewTomate = view.findViewById(R.id.cardTomate);
        cardViewMelon = view.findViewById(R.id.cardMelon);
        cardViewSandia = view.findViewById(R.id.cardSandía);
        cardViewUvas = view.findViewById(R.id.cardUvas);
        cardViewHuevo = view.findViewById(R.id.cardHuevo);
        cardViewBanano = view.findViewById(R.id.cardBanano);
        cardViewCafe = view.findViewById(R.id.cardCafe);
        cardViewJugo = view.findViewById(R.id.cardJugoFruta);
        cardViewAguaGas = view.findViewById(R.id.cardAguaConGas);

        cardViewManzana.setOnClickListener(this);
        cardViewMandarina.setOnClickListener(this);
        cardViewNaranja.setOnClickListener(this);
        cardViewTomate.setOnClickListener(this);
        cardViewMelon.setOnClickListener(this);
        cardViewSandia.setOnClickListener(this);
        cardViewUvas.setOnClickListener(this);
        cardViewHuevo.setOnClickListener(this);
        cardViewBanano.setOnClickListener(this);
        cardViewCafe.setOnClickListener(this);
        cardViewJugo.setOnClickListener(this);
        cardViewAguaGas.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
