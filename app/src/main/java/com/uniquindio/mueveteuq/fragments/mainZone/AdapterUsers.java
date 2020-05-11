package com.uniquindio.mueveteuq.fragments.mainZone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniquindio.mueveteuq.R;
import com.uniquindio.mueveteuq.models.User;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{


    Context context;
    List<User> userList;
    String username;
    String email;

    public AdapterUsers(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        //Get data
        username = userList.get(i).getNickname();
        email = userList.get(i).getEmail();

        //Set data
        holder.tvName.setText(username);
        holder.tvEmail.setText(email);


        //Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, " " + email, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{


        TextView tvName, tvEmail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


            tvName = itemView.findViewById(R.id.name_tv);
            tvEmail = itemView.findViewById(R.id.email_tv);

        }
    }

}
