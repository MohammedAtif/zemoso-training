package com.example.zemoso.dynamicviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class slidePageFragement1 extends Fragment {
    private TextView tv;
    private String gData;
    private int pos;
    private ListUpdateInterface listUpdater;


    public slidePageFragement1() {
    }

    public static slidePageFragement1 newInstance(String data, ListUpdateInterface listUpdater) {
        slidePageFragement1 f=new slidePageFragement1();
        Bundle args = new Bundle();
        args.putString("Data",data);
        f.listUpdater = listUpdater;
        f.setArguments(args);
//        Log.v("SlidePageFragment1",s);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gData=(getArguments().getString("Data","No data"));
    }

    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_slide_page_fragement1, container, false);

    return rootView;
}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv=(TextView)view.findViewById(R.id.clickhere);

    }

    @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            tv.setText(gData );
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    listUpdater.addItemToList(1, "new String added to 2");
                }
            });
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.v("SlidepageFragment","Destroy");
    }
}
