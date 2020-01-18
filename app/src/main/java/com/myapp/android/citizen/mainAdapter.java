package com.myapp.android.citizen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.mainViewHolder> {
    Intent intent;
    @NonNull
    @Override
    public mainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.card,parent,false);
        return new mainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final mainViewHolder holder, int position) {
        if(position==0){
            //intent=new Intent(holder.root.getContext(),FirRegistration.class);
            holder.title.setText("Register FIR");
            holder.subTitle.setText("Here regester an First Insedent Report Online");
            holder.imageView.setImageResource(R.drawable.mobh);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),FirRegistration.class);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }
        else if(position==1){
            holder.imageView.setImageResource(R.drawable.search);
            holder.title.setText("SEARCH FIR");
            holder.subTitle.setText("Here you can search for for the firs that you have previously registered");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),ShowFIR.class);
                    holder.root.getContext().startActivity(intent1);
                }
            });

        }else if(position==2){
            holder.title.setText("Reg. Complaint");
            holder.subTitle.setText("Here  you can register a formal comlaint in nearby police station");
            //intent=new Intent(holder.root.getContext(),CompRegister.class);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),CompRegister.class);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==3){
            holder.title.setText("Perm. for Protest");
            holder.imageView.setImageResource(R.drawable.protest);
            holder.subTitle.setText("Now  you can request for protest from concerned police station");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),PermissionForm.class);
                    intent1.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[0]);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==4){
            holder.title.setText("Perm for Procession");
            holder.imageView.setImageResource(R.drawable.procession_icon);
            holder.subTitle.setText("Request for procession from concerned authority");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),PermissionForm.class);
                    intent1.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[1]);
                    holder.root.getContext().startActivity(intent1);
                }
            });

        }else if(position==5){

            holder.title.setText("Event Permission");
            holder.subTitle.setText("Film Shooting");
            holder.imageView.setImageResource(R.drawable.filmpermission);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),PermissionForm.class);
                    intent1.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[3]);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==6){
            holder.imageView.setImageResource(R.drawable.eventpermission);
            holder.title.setText("Film Shooting");
            holder.subTitle.setText("Request permission film shotting from concerned police station");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),PermissionForm.class);
                    intent1.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[2]);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==7){
            holder.title.setText("Character Certi.");
            holder.subTitle.setText("Request for your character certificate");
            holder.imageView.setImageResource(R.drawable.page2);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),CharacterCertificate.class);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==8){
            holder.imageView.setImageResource(R.drawable.person3);
            holder.title.setText("Employee Ver");
            holder.subTitle.setText("get your employee verified and protect yourself and your business");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),EmployeeVerification.class);
                    //intent.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[0]);
                    holder.root.getContext().startActivity(intent1);
                }
            });
        }else if(position==9){
            holder.imageView.setImageResource(R.drawable.tenant_verification);
            holder.title.setText("Tenant Verf.");
            holder.subTitle.setText("Get your tenants verified and protect yourself");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(holder.root.getContext(),TenantVerification.class);
                    //intent.putExtra(PermissionClass.permissionType,PermissionClass.permissionTypes[0]);
                    holder.root.getContext().startActivity(intent1);
                }
            });

        }else if(position==10){
            holder.title.setText("S.O.S.");
            holder.subTitle.setText("one way portal for all possible s.o.s. numbers");
            holder.imageView.setImageResource(R.drawable.sos);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent=new Intent(v.getContext(),Sos_Activity.class);
                    //v.getContext().startActivity(intent);
                }
            });
        }else if(position==11){
            holder.imageView.setImageResource(R.drawable.missing_person);
            holder.title.setText("Missing Persons");
            holder.subTitle.setText("Get a list of missing persons in your locality");
        }else if(position==12){
            holder.imageView.setImageResource(R.drawable.arres);
            holder.title.setText("Recent Arrests");
            holder.subTitle.setText("Know about recent arrests in your area");
        }else if(position==13){
            holder.subTitle.setText("Know  and report about wanted criminals");
            holder.title.setText("Wanted Criminal");
            holder.imageView.setImageResource(R.drawable.rewrd_crim);
        }

    }

    @Override
    public int getItemCount() {
        return 14;
    }

    class mainViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView subTitle;
        View root;

        public mainViewHolder(@NonNull View itemView) {
            super(itemView);
            root=itemView;
            imageView =root.findViewById(R.id.mainRow_image);
            title=root.findViewById(R.id.mainRow_title);
            subTitle=root.findViewById(R.id.mainRow_subtitle);
        }
    }
}
