package com.vymo.assignment.ui.main;

import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vymo.assignment.R;
import com.vymo.assignment.model.GitResponse;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.open, R.string.Closed};
    private final Context mContext;
    private final ArrayList<GitResponse> mOpen;
    private final ArrayList<GitResponse> mClosed;

    public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<GitResponse> open, ArrayList<GitResponse> closed) {
        super(fm);
        mContext = context;
        mOpen = open;
        mClosed = closed;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new OpenFragment().newInstance(mOpen);

            case 1:
                return new ClosedFragment().newInstance(mClosed);

            default:
                return new OpenFragment().newInstance(mOpen);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}