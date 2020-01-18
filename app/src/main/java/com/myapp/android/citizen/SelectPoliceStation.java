package com.myapp.android.citizen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectPoliceStation extends DialogFragment {
    boolean okClicked;
    String  state;
    String district;
    String station;
    int sp;
    int dp;
    int psp;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    MaterialButton button_Ok;
    MaterialButton button_cancel;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okClicked=false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.select_station,container,false);
        initialize(view);
        button_Ok.setClickable(false);
        button_Ok.setEnabled(false);
        spinner1.setEnabled(false);
        spinner2.setEnabled(false);
        spinner3.setEnabled(false);
        spinner1.setClickable(false);
        spinner2.setClickable(false);
        spinner3.setClickable(false);

        final ArrayList<String> al=new ArrayList<String>();
        final ArrayList<String> al2=new ArrayList<String>();
        final ArrayList<String> al3=new ArrayList<String>();
        final String[] pid = new String[1];
        al.add("--Select--");
        databaseReference= FirebaseDatabase.getInstance().getReference("State");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //states.clear();
                int i=1;
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        //Log.i("SNAPSHOT "+i+" : ",snapshot.toString());
                        String s=snapshot.child("state").getValue().toString();
                        String sid=snapshot.child("sid").getValue().toString();
                        ++i;
                        States states1=new States(Integer.valueOf(sid),s);
                        al.add(states1.getState());
                    }
                    if(al==null||spinner1==null)
                        return;
                    if(getContext()==null||al==null||spinner1==null)
                        return;
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,al);
                    spinner1.setEnabled(true);
                    spinner1.setClickable(true);
                    spinner1.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                al2.clear();
                al2.add("--Select--");
                sp=position;
                databaseReference=FirebaseDatabase.getInstance().getReference("City").child((position)+"");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("list of state",al.toString());
                        if (dataSnapshot.exists())
                        {
                            int i=0;
                            //Log.i("DATASNAPSHOT : ",dataSnapshot.toString());
                            //DataSnapshot ds=dataSnapshot.getValue();
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Log.e("SNAPSHOT VALUE: ",snapshot.getValue().toString());

                                String c=snapshot.getValue().toString();
                                int x=c.indexOf('=');
                                int y=c.indexOf(',');
                                String c1=c.substring(x+1,y);
                                ++i;
                                al2.add(c1);
                            }
                            Log.i("My Districts",al2.toString());
                            if(al2==null||spinner2==null)
                                return;
                            if(getContext()==null||al2==null||spinner2==null)
                                return;
                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,al2);
                            spinner2.setEnabled(true);
                            spinner2.setClickable(true);
                            spinner2.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //sp=position;
                al3.clear();
                al3.add("---Select--");
                spinner3.setEnabled(false);
                spinner3.setClickable(false);
                dp=position;
                databaseReference=FirebaseDatabase.getInstance().getReference("PoliceStation").child(String.valueOf(sp-1)+String.valueOf(dp-1));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            for (DataSnapshot snapshot:dataSnapshot.getChildren())
                            {
                                String x=snapshot.getValue().toString();
                                int p=x.indexOf("policestation");
                                String add_station="";
                                for (int i1=p+14;i1<x.length();i1++)
                                {
                                    if(x.charAt(i1)==','||x.charAt(i1)==',')
                                        break;
                                    else
                                        add_station+=x.charAt(i1);
                                }
                                Log.i("POLICE STATION : ",add_station);
                                al3.add(add_station);
                            }
                            if(al3==null||spinner3==null)
                                return;
                            if(getContext()==null||al3==null||spinner3==null)
                                return;
                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,al3);
                            spinner3.setEnabled(true);
                            spinner3.setClickable(true);
                            spinner3.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    return;
                psp=position;
                button_Ok.setEnabled(true);
                button_Ok.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                Station_Getter station_getter=(Station_Getter)getActivity();
//                station_getter.OnCanceled();
            }
        });

        button_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okClicked=true;
                dismiss();
                String pid=String.valueOf(sp-1)+String.valueOf(dp-1)+String.valueOf(psp-1);
                Station_Getter sg=(Station_Getter)getActivity();
                sg.getStation(pid);
            }
        });
        return view;
    }
    private void initialize(View view) {
        button_cancel=view.findViewById(R.id.select_station_cancel);
        button_Ok=view.findViewById(R.id.select_station_ok);
        spinner1=view.findViewById(R.id.select_state);
        spinner2=view.findViewById(R.id.select_district);
        spinner3=view.findViewById(R.id.select_station);
    }
    public interface Station_Getter{
        public void getStation(String policeStationId);
        public void OnCanceled();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode== KeyEvent.KEYCODE_BACK){
                    ((Station_Getter)getActivity()).OnCanceled();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(okClicked==false)
        {
            ((Station_Getter)getActivity()).OnCanceled();
        }
    }
}
