package com.uniquindio.mueveteuq.fragments.mainZone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView recyclerView;
    public AdapterUsers adapterUsers;
    public List<User> userList;
    public List<User> resultList;
    private String cadenaBusqueda;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        //nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        recyclerView = view.findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreferences preferencias= this.getActivity().getSharedPreferences("busquedaPref", Context.MODE_PRIVATE);
        cadenaBusqueda = preferencias.getString("cadenaBusqueda", "");

        //Init user list
        userList = new ArrayList<>();
        resultList = new ArrayList<>();

        adapterUsers = new AdapterUsers(getActivity(), resultList);
        //Set adapter
        recyclerView.setAdapter(adapterUsers);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);


        //Get all users


        if(cadenaBusqueda != "")
         getSearchResult(cadenaBusqueda);

        adapterUsers = new AdapterUsers(getActivity(), resultList);
        recyclerView.setAdapter(adapterUsers);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
        if(resultList.size()!=0)
        getSearchResult(cadenaBusqueda);
**/

    }

    public List<User> getSearchResult(final String query) {

        //Get current user

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named "Users" content users info
        CollectionReference users = FirebaseFirestore.getInstance().collection("Users");

        //Get all data of collection

        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){

                        User user = new User();
                        user.setNickname((String) document.getData().get("nickname"));
                        user.setEmail((String) document.getData().get("email"));

                        //        puntosUsuario = (long) document.getData().get("accumPoints");

                        user.setAccumPoints(0);
                        user.setPassword((String) document.getData().get("password"));

                        assert fUser != null;
                        if(!user.getEmail().equals(fUser.getEmail())){

                            if(user.getNickname().toLowerCase().contains(query.toLowerCase()) ||
                                    user.getEmail().toLowerCase().contains(query.toLowerCase()) ){

                                resultList.add(user);
                       //         Toast.makeText(getActivity(), "Llega aqui", Toast.LENGTH_SHORT).show();



                            }

                        }

                       // Toast.makeText(getActivity(), "Tamaño " + resultList.size(), Toast.LENGTH_SHORT).show();

                        adapterUsers.notifyDataSetChanged();

                        adapterUsers = new AdapterUsers(getActivity(), resultList);
                        adapterUsers.notifyDataSetChanged();

                        //Set adapter
                        recyclerView.setAdapter(adapterUsers);


                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return resultList;

    }



}
