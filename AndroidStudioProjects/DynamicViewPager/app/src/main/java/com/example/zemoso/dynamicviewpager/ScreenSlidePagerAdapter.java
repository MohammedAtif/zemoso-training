package com.example.zemoso.dynamicviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zemoso on 18/11/16.
 */
/**
 * A simple pager adapter that represents  ScreenSlidePageFragment objects, in
 * sequence.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter implements ListUpdateInterface{
    private List<String> data;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<String> strings) {
        super(fm);
        data = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return slidePageFragement1.newInstance(data.get(position), this);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public void addItemToList(String item) {
        addItemToList(data.size(), item);
    }

    @Override
    public void removeItemFromList(String item) {
        data.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public void addItemToList(int position, String item) {
        data.add(position, item);
        notifyDataSetChanged();
    }

    @Override
    public void removeItemFromList(int position) {
        if(data.size() <= position){
            throw new IllegalArgumentException("Please check the position");
        }
        data.remove(position);
        notifyDataSetChanged();
    }
    // Add "view" at "position" to "views".
    // Returns position of new view.
    // The app should call this to add pages; not used by ViewPager.
}

