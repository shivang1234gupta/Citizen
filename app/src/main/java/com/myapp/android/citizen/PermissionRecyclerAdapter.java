package com.myapp.android.citizen;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.Permission;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PermissionRecyclerAdapter extends RecyclerView.Adapter<PermissionViewHolder>
{

    ArrayList<PermissionClass> permissionarrayList;
    public PermissionRecyclerAdapter(ArrayList<PermissionClass>firarrayList) {
        super();
        this.permissionarrayList=firarrayList;
    }

    @NonNull
    @Override
    public PermissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.permission_recycler_view_layout,parent,
                false);
        return new PermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissionViewHolder holder, final int position) {
        holder.statusTextView.setText("STATUS : "+permissionarrayList.get(position).getStatus()+" "+
                permissionarrayList.get(position).getApplicantName());
        Log.e("Name",permissionarrayList.get(position).getApplicantName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), FirDetailActivity.class);
                intent.putExtra("FIR",permissionarrayList.get(position));
                view.getContext().startActivity(intent);////errror
            }
        });
    }

    @Override
    public int getItemCount() {
        return permissionarrayList.size();
    }
}
class PermissionViewHolder extends RecyclerView.ViewHolder {

    //TextView firIdTextView;
    TextView statusTextView;
    View root;


    public PermissionViewHolder(@NonNull View itemView) {
        super(itemView);
        root=itemView;

        //firIdTextView = (TextView) itemView.findViewById(R.id.firIdTextView);
        statusTextView = (TextView) itemView.findViewById(R.id.PerStatusTextView);
    }
}



