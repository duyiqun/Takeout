package com.qun.takeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qun.takeout.R;
import com.qun.takeout.ui.adapter.HomeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 工作内容：
 * 1、布局
 * 2、头容器的处理
 * a.需要侵入到状态栏中
 * b.状态栏为透明
 * c.随着RecyclerView的滑动，头的透明度会变动
 * 3、RecyclerView数据加载
 * a.简单数据加载
 * b.复杂数据加载
 * Created by Qun on 2017/6/18.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @BindView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testData();
//        mRvHome.setAdapter(new HomeRecyclerViewAdapter());
        mRvHome.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mRvHome.addOnScrollListener(listener);
    }

    private void testData() {
        List<String> nearBySeller = new ArrayList<>();
        List<String> otherSeller = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            nearBySeller.add("附近商家" + i);
        }

        for (int i = 1; i < 100; i++) {
            otherSeller.add("普通商家" + i);
        }

        mRvHome.setAdapter(new HomeRecyclerViewAdapter(nearBySeller, otherSeller));
    }


    private int sumY = 0;
    private float duration = 150.0f;//在0到150之间去改变头部的透明度
    private ArgbEvaluator evaluator = new ArgbEvaluator();
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

//            System.out.println("recyclerView = [" + recyclerView + "], dx = [" + dx + "], dy = [" + dy + "]");
            sumY += dy;
            //滚动的总距离相对0到150之间有一个百分比，头部的透明度也是从初始化变动到不透明，通过距离的百分比，得到透明度对应的值
            //如果小于0那么透明度为初始值，如果大于150为不透明状态
            int bgColor = 0X553190E8;
            if (sumY < 0) {
                bgColor = 0X553190E8;
            } else if (sumY > 150) {
                bgColor = 0XFF3190E8;
            } else {
                bgColor = (int) evaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
            }
            mLlTitleContainer.setBackgroundColor(bgColor);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
