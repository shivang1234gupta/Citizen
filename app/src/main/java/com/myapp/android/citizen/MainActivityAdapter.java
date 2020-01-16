package com.myapp.android.citizen;

import android.content.Intent;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.core.Context;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List_item b=a.get(position);
        holder.heading.setText(b.getName());
        if (position==0){
        holder.heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),FIRActivity.class);////errror
                view.getContext().startActivity(intent);

            }
        });}
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView heading;
        public ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            heading=itemView.findViewById(R.id.text);
        }
    }


}
