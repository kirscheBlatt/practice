package com.example.a81903.practice;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.a81903.practice.fragment.BlankFragment;
import com.example.a81903.practice.fragment.Tab1Fragment;
import com.example.a81903.practice.fragment.Tab2Fragment;
import com.example.a81903.practice.fragment.Tab4Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("タブ1"));
        tabLayout.addTab(tabLayout.newTab().setText("タブ２"));
        tabLayout.addTab(tabLayout.newTab().setText("タブ３"));

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new Tab1Fragment();
                    break;
                case 1:
                    fragment = new Tab2Fragment();
                    break;
                case 2:
                    fragment = new BlankFragment();
                    break;
                case 3:
                    fragment = new Tab4Fragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public String getPageTitle(int position){
            String tabTitle = null;

            switch (position){
                case 0:
                    tabTitle = "おもてなし";
                    break;
                case 1:
                    tabTitle = "旬（８月）";
                    break;
                case 2:
                    tabTitle = "新着順";
                    break;
                case 3:
                    tabTitle = "人気";
                    break;
            }

            return tabTitle;
        }

    }


}
