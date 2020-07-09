package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.recycler.FriendData;
import com.example.chapter3.homework.recycler.FriendDataSet;
import com.example.chapter3.homework.recycler.FriendItemAdapter;


import java.util.List;

public class PlaceholderFragment extends Fragment {
    static public final int itemMessage = 0;
    static public final int itemFriends = 1;
    static public final int itemOthers = 2;

    public static final String ARG_OBJECT = "object";

    private int id;
    private final String TAG = "PlaceholderFragment";
    private LottieAnimationView mLoadingView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<FriendData> mFriendList;
    private FriendItemAdapter mAdapter;
    private ViewPager mPager;
    private Handler mHandler;
    public int getMyId() {
        return id;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ARG_OBJECT);
        Log.d(TAG, "onCreate called " + id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
//        Bundle args = getArguments();
//        switch(args.getInt(ARG_OBJECT)) {
//            case itemFriends:
//                break;
//            default:
//                break;
//        }
        Log.d(TAG, "onCreateView() called " + id);
        return inflater.inflate(R.layout.fragment_placeholder, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPager = getActivity().findViewById(R.id.view_pager);

        mRecyclerView = view.findViewById(R.id.recycler_vertical);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager( this.getActivity() );
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFriendList = FriendDataSet.getData();

        mAdapter = new FriendItemAdapter(mFriendList);
        mRecyclerView.setAdapter(mAdapter);

        mLoadingView = view.findViewById(R.id.lottie_animation_view);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "Current Item: " + mPager.getCurrentItem());
        Log.d(TAG, "onActivityCreated() called " + id );
    }

    public class MyRunnable implements Runnable {
        private PlaceholderFragment fragment;

        MyRunnable(PlaceholderFragment fragment) {
            super();
            this.fragment = fragment;
        }

        @Override
        public void run() {
            // 这里会在 5s 后执行
            // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
//            Log.d(TAG, "fragment.mLoadingView.id: " + fragment.mLoadingView.getId());
//            Log.d(TAG, "mLoadingView.id:" + mLoadingView.getId());
            ObjectAnimator lottieAlpha = ObjectAnimator
                    .ofFloat(mLoadingView, "alpha", mLoadingView.getAlpha(), 0F).setDuration(1000);
            ObjectAnimator listAlpha = ObjectAnimator
                    .ofFloat(mRecyclerView, "alpha", mRecyclerView.getAlpha(), 1F).setDuration(1000);
            lottieAlpha.setRepeatCount(1);
            listAlpha.setRepeatCount(1);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(lottieAlpha, listAlpha);
//            Log.d(TAG, "Show Animation " + mPager.getCurrentItem()
//                    + " The current alpha of loadingView: " + mLoadingView.getAlpha());
//            Log.d(TAG, "run on " + id);
            animatorSet.start();

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called " + id);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called " + id );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called " + id );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called " + id );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called " + id );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called " + id );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called " + id );
    }

    public void runLoadingAnimation() {
        Log.d(TAG, "Run Loading Animation");
        ObjectAnimator lottieAlpha = ObjectAnimator
                .ofFloat(mLoadingView, "alpha", mLoadingView.getAlpha(), 0F).setDuration(1000);
        ObjectAnimator listAlpha = ObjectAnimator
                .ofFloat(mRecyclerView, "alpha", mRecyclerView.getAlpha(), 1F).setDuration(1000);
        lottieAlpha.setRepeatCount(1);
        listAlpha.setRepeatCount(1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(lottieAlpha, listAlpha);
//            Log.d(TAG, "Show Animation " + mPager.getCurrentItem()
//                    + " The current alpha of loadingView: " + mLoadingView.getAlpha());
//            Log.d(TAG, "run on " + id);
        animatorSet.start();
    }

    public void startLoading() {
        Log.d(TAG, "Start Loading");
        mLoadingView.setAlpha(1F);
        mRecyclerView.setAlpha(0F);
    }
}
