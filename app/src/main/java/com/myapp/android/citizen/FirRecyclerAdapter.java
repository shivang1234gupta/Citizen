package com.myapp.android.citizen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
    public class FirRecyclerAdapter extends RecyclerView.Adapter<FirViewHolder> {

        //ArrayList<String> firIdArrayList;
        ArrayList<FIR>firarrayList;

        public FirRecyclerAdapter(ArrayList<FIR>firarrayList) {
            super();
            //this.firIdArrayList=firIdArrayList;
            this.firarrayList=firarrayList;
        }


        @NonNull
        @Override
        public FirViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fir_recycler_view_layout,parent,
                    false);
            return new FirViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FirViewHolder holder, final int position) {
            //holder.firIdTextView.setText("FIR ID : "+firIdArrayList.get(position));
            holder.statusTextView.setText("STATUS : "+firarrayList.get(position).getStatus()+" "+
                    firarrayList.get(position).getApplicantName());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), ShowFullFIRActivity.class);
                    intent.putExtra("FIR",firarrayList.get(position));
                    view.getContext().startActivity(intent);////errror
                }
            });
        }

        @Override
        public int getItemCount() {
            return firarrayList.size();
        }
    }
    class FirViewHolder extends RecyclerView.ViewHolder {

        //TextView firIdTextView;
        TextView statusTextView;
        View root;


        public FirViewHolder(@NonNull View itemView) {
            super(itemView);
            root=itemView;

            //firIdTextView = (TextView) itemView.findViewById(R.id.firIdTextView);
            statusTextView = (TextView) itemView.findViewById(R.id.FirStatusTextView);
        }
    }
