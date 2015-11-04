package com.greenpoint.patient.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.greenpoint.patient.utils.AppUtil;

import java.util.List;

public class BaseFragmentPagerAdapter  extends FragmentPagerAdapter{

    protected FragmentManager fm;

    protected List<Fragment> fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void bindData(List<Fragment> fragments){
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {

        if (AppUtil.isInvalidate(fragments)){
            return fragments.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    public void setFragments(){
        if (AppUtil.isInvalidate(fragments)){
            FragmentTransaction transaction = fm.beginTransaction();

            for (Fragment fragment:fragments){
                transaction.remove(fragment);
            }

            transaction.commit();
            fm.executePendingTransactions();
        }
    }
}
