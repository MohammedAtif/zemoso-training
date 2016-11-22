package com.example.zemoso.playerdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zemoso.playerdetails.Models.PlayerDatabase;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddDataFragment extends Fragment {
    public AddDataFragment() {
    }

    EditText playerName,playerAge,playerRank,playerImageUrl;
    private Button addData;
    Realm realm;
    private int pAge,pRank;
    private String pName,pImageUrl;
    private FloatingActionButton fab;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_add_data, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerName=(EditText)view.findViewById(R.id.playerName);
        playerAge=(EditText)view.findViewById(R.id.playerAge);
        playerRank=(EditText)view.findViewById(R.id.playerRank);
        playerImageUrl=(EditText)view.findViewById(R.id.playerImageUrl);
        addData=(Button)view.findViewById(R.id.buttonAddDataToRealm);
//        fab=(FloatingActionButton)view.findViewById(R.id.addData);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToRealm();
                pName=playerName.getText().toString();
                pAge= Integer.parseInt(playerAge.getText().toString());
                pRank= Integer.parseInt(playerRank.getText().toString());
                pImageUrl=playerImageUrl.getText().toString();
            }
        });
    }

    private void addDataToRealm() {
        Long id_val;
        realm=Realm.getDefaultInstance();
        //realm.beginTransaction();
        RealmResults<PlayerDatabase> allTransactions = realm.where(PlayerDatabase.class).findAll();
        if(allTransactions.isEmpty())
            id_val= 0L;
        else{
            id_val=allTransactions.last().getId()+1;
        }

        realm.beginTransaction();
        PlayerDatabase obj = new PlayerDatabase(id_val, pName, pImageUrl, pAge, pRank);
        realm.copyToRealmOrUpdate(obj);
        realm.commitTransaction();
//        fab.show();
        getActivity().getSupportFragmentManager().popBackStack();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(realm!=null)
            realm.close();
    }
}