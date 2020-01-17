package com.myapp.android.citizen;

import android.content.Intent;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.core.Context;

import java.security.Permission;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private ArrayList<List_item> a;
    private Context context;

    public MainActivityAdapter(ArrayList<List_item> a) {
        this.a = a;
        //this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        List_item b=a.get(position);
        holder.heading.setText(b.getName());
        if (position==0){
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Tag","Adapter item clicked");
                    Intent intent=new Intent(view.getContext(), FirRegistration.class);////errror
                    view.getContext().startActivity(intent);

                }
            }); }
        else  if(position==2){
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), CompRegister.class);////errror
                    view.getContext().startActivity(intent);

                }
            });
        }else if(position==4||position==5||position==6||position==7){
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),PermissionForm.class);
                    intent.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[position-4]);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView heading;
        public ImageView imageView;
        View root;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root=itemView;
            imageView=itemView.findViewById(R.id.image);
            heading=itemView.findViewById(R.id.text);
        }
    }


}
