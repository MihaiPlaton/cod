package com.example.android.newsfeed;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;

import com.example.android.newsfeed.fragment.Business;
import com.example.android.newsfeed.fragment.Culture;
import com.example.android.newsfeed.fragment.Environment;
import com.example.android.newsfeed.fragment.Fashion;
import com.example.android.newsfeed.fragment.Home;
import com.example.android.newsfeed.fragment.Science;
import com.example.android.newsfeed.fragment.Society;
import com.example.android.newsfeed.fragment.Sport;
import com.example.android.newsfeed.fragment.World;
import com.example.android.newsfeed.utils.Constants;



public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    private Context mContext;

        public FragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.HOME:
                return new Home();
            case Constants.WORLD:
                return new World();
            case Constants.SCIENCE:
                return new Science();
            case Constants.SPORT:
                return new Sport();
            case Constants.ENVIRONMENT:
                return new Environment();
            case Constants.SOCIETY:
                return new Society();
            case Constants.FASHION:
                return new Fashion();
            case Constants.BUSINESS:
                return new Business();
            case Constants.CULTURE:
                return new Culture();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    
    @Override
    public CharSequence getPageTitle(int position) {
        int titleResId;
        switch (position) {
            case Constants.HOME:
                titleResId = R.string.ic_title_home;
                break;
            case Constants.WORLD:
                titleResId = R.string.ic_title_world;
                break;
            case Constants.SCIENCE:
                titleResId = R.string.ic_title_science;
                break;
            case Constants.SPORT:
                titleResId = R.string.ic_title_sport;
                break;
            case Constants.ENVIRONMENT:
                titleResId = R.string.ic_title_environment;
                break;
            case Constants.SOCIETY:
                titleResId = R.string.ic_title_society;
                break;
            case Constants.FASHION:
                titleResId = R.string.ic_title_fashion;
                break;
            case Constants.BUSINESS:
                titleResId = R.string.ic_title_business;
                break;
            default:
                titleResId = R.string.ic_title_culture;
                break;
        }
        return mContext.getString(titleResId);
    }
}