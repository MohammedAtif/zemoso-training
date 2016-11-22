package com.example.zemoso.playerdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.zemoso.playerdetails.Models.PlayerDatabase;

import io.realm.Realm;

/**
 * Created by zemoso on 20/11/16.
 */

public class ScreenSlidePagerAdapter extends FragmentPagerAdapter implements ListUpdateInterface {
    private List<PlayerDatabase> data;
    Realm realm;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<PlayerDatabase> strings) {
        super(fm);
        data = strings;
    }

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position<=1)
            return PlusOneFragment.newInstance(data);
        else {
            return PlusOneFragment.newInstance(data.get(position), this);
        }
    }

    @Override
    public int getCount() {
        if (data==null)
            return 1;
        else
            return data.size();
    }

    @Override
    public void addItemToList(PlayerDatabase item) {
        addItemToList(data.size(), item);
    }

    @Override
    public void removeItemFromList(String item) {
        data.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void addItemToList(int position,PlayerDatabase item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    @Override
    public void removeItemFromList(int position) {
        if (data.size() <= position) {
            throw new IllegalArgumentException("Please check the position");
        }
        data.remove(position);
        notifyDataSetChanged();
    }
}