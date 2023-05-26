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

import com.tisco.aquaapitest.Models.ScanModel;
import com.tisco.aquaapitest.R;


import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.MyViewHolder> {
    private List<ScanModel> scanlist;

    public ScanAdapter(Context context, List<ScanModel> scanlist){
        this.scanlist = scanlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, key_id, numpass, numwarn, numfail,numunknown, numnewrisk,create,name;
        LinearLayout card;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_scan);
            key_id = itemView.findViewById(R.id.key_id);
            numpass = itemView.findViewById(R.id.resetpass);
            numwarn = itemView.findViewById(R.id.warn);
            numfail = itemView.findViewById(R.id.failed);
            numunknown = itemView.findViewById(R.id.unk);
            numnewrisk = itemView.findViewById(R.id.newrisk);
            create = itemView.findViewById(R.id.create_scan);
            name = itemView.findViewById(R.id.key_name);
            card = itemView.findViewById(R.id.scan_card);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScanModel scan = scanlist.get(position);

        holder.id.setText(scan.getId().toString());
        holder.key_id.setText(scan.getKey_id().toString());
        holder.numpass.setText(scan.getNumpass().toString());
        holder.numwarn.setText(scan.getNumwarn().toString());
        holder.numfail.setText(scan.getNumfail().toString());
        holder.numunknown.setText(scan.getNumunknown().toString());
        holder.numnewrisk.setText(scan.getNumnewrisk().toString());
        holder.name.setText(scan.getName());
        holder.create.setText(scan.getCreated().toString());


    }

    @Override
    public int getItemCount() {
        return scanlist.size();
    }
}
