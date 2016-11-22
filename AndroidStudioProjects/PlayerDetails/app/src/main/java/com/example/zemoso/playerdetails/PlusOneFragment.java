package com.example.zemoso.playerdetails;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.zemoso.playerdetails.Models.PlayerDatabase;

public class PlusOneFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Data = "Data";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    // TODO: Rename and change types of parameters
    private TextView pName,pAge,pRaank,purl;
    private FloatingActionButton mPlusOneButton;

    private ArrayList<? extends Parcelable> pData;
    private PlayerDatabase obj;
    public PlusOneFragment() {
        // Required empty public constructor
    }

    public static PlusOneFragment newInstance(List<PlayerDatabase> data) {
        PlusOneFragment frag = new PlusOneFragment();
        return frag;
    }

    // TODO: Rename and change types and number of parameters
    public PlusOneFragment newInstance(PlayerDatabase data, ListUpdateInterface ls) {
        PlusOneFragment fragment = new PlusOneFragment();
        obj=data;
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            pData = getArguments().getParcelableArrayList(Data);
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);

        //Find the +1 button
        mPlusOneButton = (FloatingActionButton) view.findViewById(R.id.addData);
        mPlusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDataFragment newFragment = new AddDataFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}