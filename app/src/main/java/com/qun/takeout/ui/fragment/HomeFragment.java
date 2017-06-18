package com.qun.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qun.takeout.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.tv)).setText("首页");
    }
}
