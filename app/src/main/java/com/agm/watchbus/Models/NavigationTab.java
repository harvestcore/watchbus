package com.agm.watchbus.Models;

import com.agm.watchbus.FragmentType;

public class NavigationTab {
    public String title;
    public int icon;
    public FragmentType fragment;

    public NavigationTab(String title, int icon, FragmentType fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }
}
