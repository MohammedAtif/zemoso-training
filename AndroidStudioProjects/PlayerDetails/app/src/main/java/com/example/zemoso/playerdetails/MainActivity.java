package com.example.zemoso.playerdetails;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import com.example.zemoso.playerdetails.Models.PlayerDatabase;

import io.realm.Realm;

public class MainActivity extends FragmentActivity{
    Realm realm;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private List<PlayerDatabase> strings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm.getDefaultInstance();
        realm.beginTransaction();
        ArrayList<PlayerDatabase> list = new ArrayList<>(realm.where(PlayerDatabase.class).findAll());
        realm.commitTransaction();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(2);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), list);
        mPager.setAdapter(mPagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
