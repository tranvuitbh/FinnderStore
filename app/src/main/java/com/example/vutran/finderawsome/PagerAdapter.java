package com.example.vutran.finderawsome;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vutran.finderawsome.Store.ListStoreFragment;
import com.example.vutran.finderawsome.User.FavouriteFragment;

/**
 * Created by VuTran on 5/25/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ListStoreFragment();
                break;
            case 1:
                fragment = new MapFragment();
                break;
            case 2:
                fragment = new FavouriteFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0:
                title ="List Stores";
                break;
            case 1:
                title = "Around Here";
                break;
            case 2:
                title= "Saved Places";
                break;
        }
        return title;
    }
}
