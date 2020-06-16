package com.uniquindio.mueveteuq.fragments.mainZone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.util.Utilities;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.util.Objects;

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
    SharedPreferences spr;


    private String currentNickname;
    private int currentPoints;

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

        spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);

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
                realizarPago("helado de crema", 170);
                break;

            //80 puntos
            case R.id.cardPaleta:
                realizarPago("paleta", 80);

                break;

            //125 puntos
            case R.id.cardGaseosa:
                realizarPago("gaseosa (un vaso)", 125);

                break;

            //300 puntos
            case R.id.cardChocolateCake:
                realizarPago("torta de chocolate", 300);

                break;

            //100 puntos
            case R.id.cardEspaguetis:
                realizarPago("espaguetis", 100);

                break;

            //150 puntos
            case R.id.cardPolloAsado:
                realizarPago("pollo asado", 150);

                break;

            //60 puntos
            case R.id.cardGelatina:
                realizarPago("gelatina", 60);

                break;

            //270 puntos
            case R.id.cardTortaLimon:
                realizarPago("torta de limon", 270);

                break;

            //240 puntos
            case R.id.cardHamburguesa:
                realizarPago("hamburguesa", 240);

                break;

            //160 puntos
            case R.id.cardPizza:
                realizarPago("pizza", 160);

                break;

            //150 puntos
            case R.id.cardPapasFritas:
                realizarPago("papas fritas", 150);

                break;

            //125 puntos
            case R.id.cardChocoMilk:
                realizarPago("leche achocolatada", 125);

                break;


        }


    }




    private void realizarPago(String nombreR, final int points) {

        currentNickname = spr.getString("currentUser", "");
        currentPoints = spr.getInt("currentPoints", 0);

        //Si los puntos del usuario son mayores que lo que vale la recompensa
        //Ej: Si el usuario tiene 200 puntos y el producto vale 150
        //Transacción exitosa

        if (currentPoints >= points) {

            MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(getActivity())
                    .setTitle(nombreR.toUpperCase())
                    .setDescription("¡Hola! Con esto podrás consumir " + nombreR + " en la vida real a cambio de tus esfuerzos. Esto te costará " + points + " puntos. ¿Deseas continuar?")
                    .setStyle(Style.HEADER_WITH_TITLE)
                    .setPositiveText("Aceptar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            
                            
                            int newPoints = currentPoints - points;
                            
                            updatePoints(newPoints);


                        }
                    })
                    .setNegativeText("Cancelar")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .build();

            dialog.show();


        }

        //Si al usuario no le alcanza para el producto - Los puntos que tiene el usuario no son superiores
        //a los de la recompensa
        //Transacción fallida
        else if (currentPoints < points) {


            MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(getActivity())
                    .setTitle(nombreR.toUpperCase())
                    .setDescription("¡Lo sentimos! No te alcanza para consumir esto.")
                    .setStyle(Style.HEADER_WITH_TITLE)
                    .setPositiveText("Cancelar")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).build();

            dialog.show();

        }


    }

    private void updatePoints(final int newPoints) {

        Utilities.init(getActivity());
        Utilities.showProgressBar();

        if(UtilsNetwork.isOnline(getActivity())) {

            users.whereEqualTo("nickname", currentNickname).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            users.document(currentNickname).update("accumPoints", newPoints).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("tag", "Nota de desarrolladora: Puntuación actualizada con éxito");

                                    SharedPreferences.Editor objetoEditor = spr.edit();
                                    objetoEditor.putInt("currentPoints", newPoints);
                                    objetoEditor.apply();
                                    Utilities.dismissProgressBar();


                                    Toast.makeText(getActivity(), "¡Disfrútalo! Ahora te quedan " + newPoints + " puntos", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("tag", "Error escribiendo documento", e);

                                }
                            });


                        }
                    }


                }
            });
        }else{
            Toast.makeText(getActivity(), R.string.connection_missing, Toast.LENGTH_SHORT).show();

        }
    }







}
