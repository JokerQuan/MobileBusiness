package com.wq.mobilebusiness.home;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.home.active.NewActiveFragment;
import com.wq.mobilebusiness.home.active.OldActiveFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/8.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "-----Encounter-----";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> fragmentsList = new ArrayList<Fragment>();

    private Fragment newActive;
    private Fragment oldActive;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        mTabLayout = (TabLayout)view.findViewById(R.id.tl_main);
        mViewPager = (ViewPager)view.findViewById(R.id.vp_main);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.new_active));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.old_active));

        newActive = new NewActiveFragment();
        oldActive = new OldActiveFragment();

        if (fragmentsList.size() == 0){
            fragmentsList.add(newActive);
            fragmentsList.add(oldActive);
        }


        initViewPager();

        return view;
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragmentsList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentsList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                Resources res = getResources();
                String[] title = new String[]{res.getString(R.string.new_active),
                        res.getString(R.string.old_active)};
                return title[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }
}
