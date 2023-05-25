package com.tisco.aquaapitest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tisco.aquaapitest.Models.UserModel;
import com.tisco.aquaapitest.R;
import com.tisco.aquaapitest.UserDetail;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<UserModel> userlist;
    private Context context;

    public UserAdapter(Context context, List<UserModel> userlist){
        this.context = context;
        this.userlist = userlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, email, comfirmed, created;
        LinearLayout card;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_user);
            email = itemView.findViewById(R.id.email_user);
            comfirmed = itemView.findViewById(R.id.comfirm_sts);
            created = itemView.findViewById(R.id.create);
            card = itemView.findViewById(R.id.user_card);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel user = userlist.get(position);
        Intent intent = new Intent(context, UserDetail.class);

        holder.card.setOnClickListener(v -> {
            intent.putExtra("iduser",user.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });
        holder.id.setText(user.getId().toString());
        holder.email.setText(user.getEmail());
        if (user.getConfirmed() == true){
            holder.comfirmed.setText("confirmed");
            holder.comfirmed.setTextColor(Color.GREEN);
        } else {
            holder.comfirmed.setText("unconfirmed");
            holder.comfirmed.setTextColor(Color.RED);
        }
        holder.created.setText(user.getCreated().toString());


    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}
