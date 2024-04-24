package com.dilip.chatapp;

import androidx.fragment.app.FragmentPagerAdapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // Optimize for better memory usage
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}


/*
public class ViewPagerMessengerAdapter extends FragmentPagerAdapter {

    // Reference to the FragmentManager for creating fragments
    private final FragmentManager fragmentManager;

    public ViewPagerMessengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return a new fragment based on the current position
        switch (position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new UpdatesFragment();
            default:
                return new CallsFragment();
        }
    }

    @Override
    public int getCount() {
        // Return the total number of tabs (3 in this case)
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Provide titles for each tab based on position
        switch (position) {
            case 0:
                return "Chats";
            case 1:
                return "Updates";
            default:
                return "Calls";
        }
    }
}*/
