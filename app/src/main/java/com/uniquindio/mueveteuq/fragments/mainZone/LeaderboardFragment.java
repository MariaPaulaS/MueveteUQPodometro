package com.uniquindio.mueveteuq.fragments.mainZone;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.Race;
import com.uniquindio.mueveteuq.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Race> recordList;
    TableLayout tabla;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recordList = new ArrayList<>();


        tabla = view.findViewById(R.id.tabla_puntuaciones);


        recordList = getRecordList();

        return view;

    }



    public List<Race> getRecordList() {

        //Get current user

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named "Users" content users info
        CollectionReference records = FirebaseFirestore.getInstance().collection("Records");

        //Get all data of collection

        records.orderBy("pasos", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                        int pasos = Integer.parseInt(document.getData().get("pasos").toString()) ;

                        fijarColumna( (String) document.getData().get("nicknameUsuario"), pasos);
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return recordList;

    }


    public void fijarColumna(String nicknameV, int pasosV){



        TableRow row = new TableRow(getActivity().getBaseContext());

                TextView nickname;
                TextView pasos;


                nickname = new TextView(getActivity().getBaseContext());
                pasos = new TextView(getActivity().getBaseContext());
                nickname.setGravity(Gravity.CENTER_HORIZONTAL);
                pasos.setGravity(Gravity.CENTER_HORIZONTAL);
                nickname.setPadding(40, 15, 15, 15);
                pasos.setPadding(110, 15, 15, 15);
                nickname.setText(nicknameV);
                pasos.setText(pasosV + "");
                nickname.setTextColor(getResources().getColor(R.color.lila));
                pasos.setTextColor(Color.BLACK);
                row.addView(nickname);
                row.addView(pasos);
                tabla.addView(row);


    }


}
