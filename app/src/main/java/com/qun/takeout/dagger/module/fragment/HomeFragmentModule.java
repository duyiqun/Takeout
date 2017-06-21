package com.qun.takeout.dagger.module.fragment;

import com.qun.takeout.presenter.fragment.HomeFragmentPresenter;
import com.qun.takeout.ui.fragment.HomeFragment;

import dagger.Provides;

/**
 * Created by Qun on 2017/6/20.
 */

public class HomeFragmentModule {

    HomeFragment mHomeFragment;

    @Provides
    HomeFragmentPresenter provideHomeFragmentPresenter() {
        return new HomeFragmentPresenter();
    }
}
