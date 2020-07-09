package com.example.chapter3.homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 使用 ViewPager 和 Fragment 做一个简单版的好友列表界面
 * 1. 使用 ViewPager 和 Fragment 做个可滑动界面
 * 2. 使用 TabLayout 添加 Tab 支持
 * 3. 对于好友列表 Fragment，使用 Lottie 实现 Loading 效果，在 5s 后展示实际的列表，要求这里的动效是淡入淡出
 */
public class Ch3Ex3Activity extends AppCompatActivity {
    private static final String TAG = "Ch3Ex3Activity";
    private Handler mHandler;
    private static final int PAGE_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3ex3);
        final ViewPager pager = findViewById(R.id.view_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                // How many PlaceholderFragment in ViewPager
                int count = 0;
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if(fragment instanceof PlaceholderFragment) {
                        count ++;
                    }
                }
                Log.d(TAG, "ViewPager has " + count + " PlaceholderFragemnt");
                Log.d(TAG, "pager.getCurrentItem(): " + pager.getCurrentItem());
                Log.d(TAG, "i: " + i);
                final Fragment curFragment = (Fragment) pager.getAdapter().instantiateItem(pager, pager.getCurrentItem());
                if(curFragment instanceof PlaceholderFragment && pager.getCurrentItem() == i) {
                    // set loading
                    Log.d(TAG, "postDelayed: " + i);
                    final PlaceholderFragment curPlaceholderFragment = (PlaceholderFragment) curFragment;
                    curPlaceholderFragment.startLoading();
                    mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            curPlaceholderFragment.runLoadingAnimation();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Fragment fragment;
                if(i == 0) {
                    fragment= new PlaceholderFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(PlaceholderFragment.ARG_OBJECT, i);
                    fragment.setArguments(args);
                }
                else {
                    fragment = new FragmentA();
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String result;
                switch (position) {
                    case PlaceholderFragment.itemMessage:
                        result = "消息";
                        break;
                    case PlaceholderFragment.itemFriends:
                        result = "好友";
                        break;
                    default:
                        result = "其他";
                        break;
                }
                return result;
            }
        });
        tabLayout.setupWithViewPager(pager);
    }

}
