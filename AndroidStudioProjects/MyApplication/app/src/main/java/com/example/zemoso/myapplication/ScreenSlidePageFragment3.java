package com.example.zemoso.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import Models.Data;
import io.realm.Realm;

import static android.content.Context.MODE_PRIVATE;


public class ScreenSlidePageFragment3 extends Fragment {
    public EditText eData;
    private Realm realm;
    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page3, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eData=(EditText) view.findViewById(R.id.editText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                SharedPreferences.Editor editor = context.getSharedPreferences("ShPref", MODE_PRIVATE).edit();
//                editor.putString("data", eData.getText().toString());
//                editor.commit();
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.shdata), eData.getText().toString());
                editor.commit();

//
//                realm = Realm.getDefaultInstance();
//                realm.beginTransaction();
//                Data data = realm.createObject(Data.class);
//                data.setData(eData.getText().toString());
//                realm.commitTransaction();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
