package com.hbsf.home.view.news.fragmentadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsFragmentAdapter extends FragmentPagerAdapter {
    private List<String> tabsID;
    private List<String> tabsName;
    private List<Fragment> tabFragmentList;

    public NewsFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabsID = new ArrayList<>();
        tabsName = new ArrayList<>();
        tabFragmentList = new ArrayList<>();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return getTabsName().get(position);
    }

    public List<String> getTabsID() {
        return tabsID;
    }

    public void setTabsID(List<String> tabsID) {
        this.tabsID = tabsID;
    }

    public List<String> getTabsName() {
        return tabsName;
    }

    public void setTabsName(List<String> tabsName) {
        this.tabsName = tabsName;
    }

    public List<Fragment> getTabFragmentList() {
        return tabFragmentList;
    }

    public void setTabFragmentList(List<Fragment> tabFragmentList) {
        this.tabFragmentList = tabFragmentList;
    }
}
