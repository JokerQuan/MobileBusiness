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
import com.wq.mobilebusiness.home.encounter.EncountedFragment;
import com.wq.mobilebusiness.home.encounter.ImHereFragment;
import com.wq.mobilebusiness.home.encounter.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/8.
 */
public class EncounterFragment extends Fragment {
    private static final String TAG = "-----Encounter-----";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> fragmentsList = new ArrayList<Fragment>();

    private Fragment user;
    private Fragment encounted;
    private Fragment imhere;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encounter,container,false);

        mTabLayout = (TabLayout)view.findViewById(R.id.tl_encounter);
        mViewPager = (ViewPager)view.findViewById(R.id.vp_encounter);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.encounter));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.encounted));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.im_here));

        user = new UserFragment();
        encounted = new EncountedFragment();
        imhere = new ImHereFragment();

        if (fragmentsList.size() == 0){
            fragmentsList.add(user);
            fragmentsList.add(encounted);
            fragmentsList.add(imhere);
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
                String[] title = new String[]{res.getString(R.string.encounter),
                        res.getString(R.string.encounted),
                        res.getString(R.string.im_here)};
                return title[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

}
