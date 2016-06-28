package com.example.asvenugo.bottomtabs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asvenugo.bottomtabs.fragment.DateFragment;
import com.example.asvenugo.bottomtabs.fragment.MailFragment;
import com.example.asvenugo.bottomtabs.fragment.MapFragment;
import com.example.asvenugo.bottomtabs.fragment.PictureFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: return DateFragment.newInstance();
            case 1: return MapFragment.newInstance();
            case 2: return PictureFragment.newInstance();
            case 3: return MailFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Date";
            case 1:
                return "Map";
            case 2:
                return "Picture";
            case 3: return "Mail";
        }
        return null;
    }
}
