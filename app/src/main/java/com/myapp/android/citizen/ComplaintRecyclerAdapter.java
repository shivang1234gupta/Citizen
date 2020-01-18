package com.myapp.android.citizen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintViewHolder> {

    ArrayList<Complaint> firarrayList;

    public ComplaintRecyclerAdapter(ArrayList<Complaint>firarrayList) {
        super();
        //this.firIdArrayList=firIdArrayList;
        this.firarrayList=firarrayList;
    }


    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_recycler_view_layout,parent,false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, final int position) {
        //holder.firIdTextView.setText("FIR ID : "+firIdArrayList.get(position));
        holder.statusTextView.setText("STATUS : "+firarrayList.get(position).getApplicant()+" "+
                firarrayList.get(position).getDateOfReg());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), FirDetailActivity.class);
                intent.putExtra("COMPLAINT", (Serializable) firarrayList.get(position));
                view.getContext().startActivity(intent);////errror
            }
        });
    }

    @Override
    public int getItemCount() {
        return firarrayList.size();
    }
}
    class ComplaintViewHolder extends RecyclerView.ViewHolder {

    //TextView firIdTextView;
    TextView statusTextView;
    View root;


    public ComplaintViewHolder(@NonNull View itemView) {
        super(itemView);
        root=itemView;

        //firIdTextView = (TextView) itemView.findViewById(R.id.firIdTextView);
        statusTextView = (TextView) itemView.findViewById(R.id.FirStatusTextView);
    }
}

