package com.uniquindio.mueveteuq.fragments.mainZone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.util.Utilities;
import com.uniquindio.mueveteuq.util.UtilsNetwork;

import java.util.Objects;
import java.util.regex.Pattern;


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


    private FirebaseFirestore db;
    private CollectionReference users;
    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();


    private String nicknameKey;
    private String passwordCurrent;
    private String emailCurrent;

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

        final SharedPreferences spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);
        nicknameKey = spr.getString("currentUser", "");
        passwordCurrent = spr.getString("passwordCurrentUser", "");
        emailCurrent = spr.getString("emailCurrentUser", "");

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

        if (id == R.id.title_email || id == R.id.title_email_description) {

            showUpdateDialogData("email");

        } else if (id == R.id.title_password || id == R.id.title_password_description) {
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

                final String value = editText.getText().toString().trim();
                if (userFirebase != null) {
                    if (key.equals("email")) {


                        Utilities.showProgressBar();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(emailCurrent, passwordCurrent);

                        // Prompt the user to re-provide their sign-in credentials
                        userFirebase.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("TAG", "User re-authenticated.");
                                        if (task.isSuccessful()) {
                                            updateEmailUser(value);
                                        } else {
                                            // Password is incorrect
                                        }
                                    }
                                });


                        //   updateEmailUser(value);

                    } else if (key.equals("contraseña")) {

                        Utilities.showProgressBar();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(emailCurrent, passwordCurrent);

                        // Prompt the user to re-provide their sign-in credentials
                        userFirebase.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("TAG", "User re-authenticated.");
                                        if (task.isSuccessful()) {
                                            updatePasswordUser(value);
                                        } else {
                                            // Password is incorrect
                                        }
                                    }
                                });

                        //         updatePasswordUser(value);

                    }
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


    /**
     * Método para actualizar la contraseña
     *
     * @param password
     */
    private void updatePasswordUser(final String password) {

        //La contraseña no puede estar vacía
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        //La contraseña no puede ser menor de 8 caracteres
        else if (password.length() < 8) {
            Toast.makeText(getActivity(), "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
            return;
        } else {



            userFirebase.updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "User password updated.");
                            }
                        }
                    });


            final SharedPreferences spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);


            users.whereEqualTo("nickname", nicknameKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                            users.document(nicknameKey).update("password", password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Utilities.dismissProgressBar();
                                    Log.d("tag", "Nota de desarrolladora: Puntuación actualizada con éxito");

                                    SharedPreferences.Editor objetoEditor = spr.edit();
                                    objetoEditor.putString("passwordCurrentUser", password);
                                    objetoEditor.apply();
                                    Toast.makeText(getActivity(), "¡Listo! Recuérdala para el próximo inicio de sesión.", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Utilities.dismissProgressBar();
                                    Log.w("tag", "Error escribiendo documento", e);

                                }
                            });

                        }
                    }
                }
            });


        }
    }


    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    /**
     * Metodo para actualizar email
     *
     * @param email
     */
    private void updateEmailUser(final String email) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Por favor ingrese nuevo email", Toast.LENGTH_SHORT).show();
        } else if (!validarEmail(email)) {

            Toast.makeText(getActivity(), "Por favor ingrese un email valido", Toast.LENGTH_SHORT).show();
        } else {


         //   Utilities.showProgressBar();
            userFirebase.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Log.d("TAG", "User email address updated.");
                        changeEmailDatabase(email);

                    }

                    else{
                        Utilities.dismissProgressBar();
                        Toast.makeText(getActivity(), "¡Lo sentimos! Ya hay otro usuario registrado con este e-mail.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });




        }

    }


    public void changeEmailDatabase(final String email){

        final SharedPreferences spr = this.getActivity().getSharedPreferences("userCurrentPreferences", Context.MODE_PRIVATE);


        users.whereEqualTo("nickname", nicknameKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                        users.document(nicknameKey).update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Utilities.dismissProgressBar();
                                Log.d("tag", "Nota de desarrolladora: Puntuación actualizada con éxito");

                                SharedPreferences.Editor objetoEditor = spr.edit();
                                objetoEditor.putString("emailCurrentUser", email);
                                objetoEditor.apply();
                                Toast.makeText(getActivity(), "¡Listo! Recuérdalo para el próximo inicio de sesión.", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utilities.dismissProgressBar();
                                Log.w("tag", "Error escribiendo documento", e);

                            }
                        });

                    }
                }
            }
        });

    }

}
