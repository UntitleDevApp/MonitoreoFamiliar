package com.untitledev.monitoreofamiliar.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.untitledev.monitoreofamiliar.fragments.ContactFragment;
import com.untitledev.monitoreofamiliar.fragments.MonitoringFragment;

/**
 * Created by Cipriano on 2/13/2018.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{
    private int numberOfTsb;
    public PagerAdapter(FragmentManager fm, int numberOfTsb) {
        super(fm);
        this.numberOfTsb = numberOfTsb;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ContactFragment();
            case 1:
                return new MonitoringFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTsb;
    }
}
