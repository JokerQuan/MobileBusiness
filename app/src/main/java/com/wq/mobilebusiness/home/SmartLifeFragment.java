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
import com.wq.mobilebusiness.home.smartlife.BusinessFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/8.
 */
public class SmartLifeFragment extends Fragment {
    private static final String TAG = "-----Encounter-----";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> fragmentsList = new ArrayList<Fragment>();

    private Fragment business;
    private Fragment product;
    private Fragment win;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_life,container,false);

        mTabLayout = (TabLayout)view.findViewById(R.id.tl_smart_life);
        mViewPager = (ViewPager)view.findViewById(R.id.vp_smart_life);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.find_business));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.find_product));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.win_record));

        business = new BusinessFragment();
        product = new BusinessFragment();
        win = new BusinessFragment();

        if (fragmentsList.size() == 0){
            fragmentsList.add(business);
            fragmentsList.add(product);
            fragmentsList.add(win);
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
                String[] title = new String[]{res.getString(R.string.find_business),
                        res.getString(R.string.find_product),
                        res.getString(R.string.win_record)};
                return title[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

}
