package com.example.zemoso.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Models.Data;
import io.realm.Realm;
import io.realm.RealmResults;


public class ScreenSlidePageFragment extends Fragment {
    TextView tv;
    private Realm realm;
//    public Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv=(TextView)view.findViewById(R.id.textView1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realm = Realm.getDefaultInstance();
//        try {
//        final RealmResults<Data> datas = realm.where(Data.class).findAll();
        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String data = sharedPref.getString(getString(R.string.shdata), "not Present");
                Log.v("Sh data",data);
                tv.setText(data);
            }
        });
//        String data = sharedPref.getString(getString(R.string.shdata), "not Present");
//        Log.v("Sh data",data);

//        datas.addChangeListener(new RealmChangeListener<RealmResults<Data>>() {
//            @Override
//            public void onChange(RealmResults<Data> element) {
//                RealmResults<Data> allTransactions = realm.where(Data.class).findAll();
//                String lastData = allTransactions.last().getData();
//                tv.setText(lastData);
//                Log.v("This is db data",lastData);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
