package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Fragments.FragmentCalendar;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Fragments.FragmentReminder;

/**
 * Created by xinyunxing on 12/9/2014.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    //ask the fragment backstack so we do not have to create it every time even though it looks like
    //we are creating it every time in getItem
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

    }


    //int i is the position of the item
    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                return new FragmentReminder();
            case 1:
                return new FragmentCalendar();
            default:
                return new FragmentReminder();

        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "FragmentReminder";
            case 1:
                return "FragmentCalendar";
            default:
                return "FragmentReminder";

        }
        //return super.getPageTitle(position);
    }

    //number of fragment
    @Override
    public int getCount() {
        return 2;
    }
}
