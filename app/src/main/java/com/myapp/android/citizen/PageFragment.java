package com.myapp.android.citizen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private TextView textView;
    private Button button;
    private ImageView imageView;
    public PageFragment() {
        // Required empty public constructor
    }

    /*
    In fragment_page.xml create a frame which will be common in all pageviewewr slides
    and then we will extract the text present in R.string
    according to value of position
    for this purpose a getter and setter class of every field present in fragment_page must be there
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_page, container, false);
        textView=view.findViewById(R.id.pageno);
        String s=getArguments().getString("message");
        textView.setText(s);
        imageView=view.findViewById(R.id.page_image);
        int x=getArguments().getInt("position");
        if (x==1){
            Glide.with(this)
                    .load(R.drawable.group469)
                    .into(imageView);
        }
        if (x==2){
            Glide.with(this)
                    .load(R.drawable.group471)
                    .override(500,500)
                    .into(imageView);
        }
        if(x==3)
        {
            Glide.with(this)
                    .load(R.drawable.group472)
                    .override(500,500)
                    .into(imageView);
            //imageView.setImageResource(R.drawable.group472);
        }
        if (x==4)
        {
            Glide.with(this)
                    .load(R.drawable.group473)
                    .override(500,500)
                    .into(imageView);
            button=view.findViewById(R.id.swap);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),LogInActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

}

