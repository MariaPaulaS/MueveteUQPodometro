package com.uniquindio.mueveteuq.fragments.mainZone;

import android.app.AlertDialog;
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
 * Use the {@link LightRewardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightRewardFragment extends Fragment implements View.OnClickListener {
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


    private String currentNickname;
    private int currentPoints;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference users;
    SharedPreferences spr;

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

        users = db.collection("Users");

        spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);

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

        switch (view.getId()) {

            //45 puntos
            case R.id.cardManzana:
                realizarPago("manzana", 45);
                break;

            //35 puntos
            case R.id.cardMandarina:
                realizarPago("mandarina", 35);

                break;

            //44 puntos
            case R.id.cardNaranja:
                realizarPago("naranja", 44);

                break;


            //20 puntos
            case R.id.cardTomate:
                realizarPago("tomate", 20);

                break;

            //25 puntos
            case R.id.cardMelon:
                realizarPago("melón", 25);
                break;

            //20 puntos
            case R.id.cardSandía:
                realizarPago("sandía", 20);

                break;

            //73 puntos
            case R.id.cardUvas:
                realizarPago("uvas", 73);

                break;

            //100 puntos
            case R.id.cardHuevo:
                realizarPago("huevo", 100);

                break;

            //89 puntos
            case R.id.cardBanano:
                realizarPago("banano", 89);

                break;

            //2 puntos
            case R.id.cardCafe:
                realizarPago("una taza de café", 2);

                break;

            //90 puntos
            case R.id.cardJugoFruta:
                realizarPago("jugo de fruta", 90);

                break;

            //21 puntos
            case R.id.cardAguaConGas:
                realizarPago("agua con gas", 21);

                break;


        }


    }


    private void realizarPago(String nombreR, final int points) {

        final SharedPreferences spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);
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
