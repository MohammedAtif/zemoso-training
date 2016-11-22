package com.example.zemoso.imageurls;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import Models.ImageData;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by zemoso on 16/11/16.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<FeedItem> dList;
    private Context context;
    private StoreToLocal storeToLocal;
    private Realm realm;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView url;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            url = (TextView) view.findViewById(R.id.url);
            imageView = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public Adapter(List<FeedItem> dList,Context context) {
        this.dList = dList;
        this.context=context;
        if(context instanceof StoreToLocal){
            storeToLocal = (StoreToLocal) context;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FeedItem feed = dList.get(position);
        holder.url.setText(feed.getUrl());
        holder.imageView.setImageResource(android.R.color.transparent);
        holder.imageView.setVisibility(View.GONE);
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                if (!(realm.isEmpty())) {
                    RealmResults<ImageData> allTransactions = realm.where(ImageData.class).findAll();
                    String lasturl = allTransactions.last().getLocalUrl();
                    realm.commitTransaction();
                    if(lasturl.compareTo(feed.getUrl())==0){
                        File sdcard = Environment.getExternalStorageDirectory();
                        File file = new File(sdcard + "/Imageurl/imageName.jpg");
                        File dir = file.getParentFile();
                        holder.imageView.setVisibility(View.VISIBLE);
                        Glide.with(context).load(dir+"/imageName.jpg").into(holder.imageView);
                        Log.v("From",dir.toString());
                    }
                    else {
                        holder.imageView.setVisibility(View.VISIBLE);
                        Glide.with(context).load(feed.getUrl()).into(holder.imageView);
                        Log.v("Url different"," Image loaded From"+feed.getUrl());
                        if (storeToLocal != null) {
                            storeToLocal.storeToLocal(feed.getUrl());
                        }
                    }
                } else {
                    realm.commitTransaction();
                    holder.imageView.setVisibility(View.VISIBLE);
                    Glide.with(context).load(feed.getUrl()).into(holder.imageView);
                    Log.v("From",feed.getUrl());
                    if (storeToLocal != null) {
                        storeToLocal.storeToLocal(feed.getUrl());
                    }
                }
            }
        });
    }

    private void storeToLocal(){

    }


    @Override
    public int getItemCount() {
        return dList.size();
    }
}