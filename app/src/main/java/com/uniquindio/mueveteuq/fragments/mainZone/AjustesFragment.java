package com.uniquindio.mueveteuq.fragments.mainZone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.util.Utilities;


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



    TextView emailTv;
    TextView emailDescriptionTv;
    TextView passwordTv;
    TextView passwordDescriptionTv;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private CollectionReference users;
    EmailAuthProvider emailProvider;

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


        emailTv = view.findViewById(R.id.title_email);
        emailDescriptionTv = view.findViewById(R.id.title_email_description);
        passwordTv = view.findViewById(R.id.title_password);
        passwordDescriptionTv = view.findViewById(R.id.title_password_description);


        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        users = db.collection("Users");

        emailTv.setOnClickListener(this);
        emailDescriptionTv.setOnClickListener(this);
        passwordTv.setOnClickListener(this);
        passwordDescriptionTv.setOnClickListener(this);
        Utilities.init(getActivity());

        return view;


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.title_email || id == R.id.title_email_description){

            showUpdateDialogData("email");

        }

        else if(id == R.id.title_password || id == R.id.title_password_description){
            showUpdateDialogData("contraseña");
        }

    }

    private void showUpdateDialogData(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Actualizar " + key);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        final EditText editText = new EditText(getActivity());
        editText.setHint("Nuevo " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("LISTO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String value = editText.getText().toString().trim();

                if(TextUtils.isEmpty(value)){
                    Toast.makeText(getActivity(), "Por favor ingrese nuevo " + key, Toast.LENGTH_SHORT).show();
                }

                else {

                }

            }
        });

        builder.setNegativeButton("MEJOR NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create().show();

    }
}
