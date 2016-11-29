package com.example.zemoso.uistuff;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zemoso on 28/11/16.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Data> dList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView Name;
        public ImageView profPic;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.profileName);
            profPic = (ImageView) view.findViewById(R.id.profilepicimageView);

        }
    }
    public Adapter(List<Data> list){
        dList=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data = dList.get(position);
        holder.Name.setText(data.getName());
//        holder.genre.setText(movie.getGenre());
        holder.profPic.setImageBitmap(data.getImg());

    }

    @Override
    public int getItemCount() {
        return dList.size();
    }

}
