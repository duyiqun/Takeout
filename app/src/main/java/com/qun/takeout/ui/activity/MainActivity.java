package com.qun.takeout.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qun.takeout.R;
import com.qun.takeout.ui.fragment.HomeFragment;
import com.qun.takeout.ui.fragment.MoreFragment;
import com.qun.takeout.ui.fragment.OrderFragment;
import com.qun.takeout.ui.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    //底部导航容器
    @BindView(R.id.main_bottom_switcher_container)
    LinearLayout mMainBottomSwitcherContainer;
    ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        setListener();
    }

    private void init() {
        mFragments.add(new HomeFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new UserFragment());
        mFragments.add(new MoreFragment());

        onClickListener.onClick(mMainBottomSwitcherContainer.getChildAt(0));
    }

    /**
     * 需求：完成一个通用底部导航的处理
     */
    private void setListener() {
        //所有孩子，不包括孙子
        int childCount = mMainBottomSwitcherContainer.getChildCount();

        for (int i = 0; i < childCount; i++) {
            FrameLayout childAt = (FrameLayout) mMainBottomSwitcherContainer.getChildAt(i);
            childAt.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = mMainBottomSwitcherContainer.indexOfChild(v);
            changeUI(index);
            changeFragment(index);
        }
    };

    /**
     * 改变index对应的孩子的状态，包括这个孩子中所有空间的状态(不可用状态，enable=false)
     * 改变其他的孩子的状态，包括这些孩子中所有空间的状态
     *
     * @param index
     */
    private void changeUI(int index) {
        int childCount = mMainBottomSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //判断i是否与index相同
            //相同：不可用状态：enable=false
            if (i == index) {
                //不可以再点击了
//                mMainBottomSwitcherContainer.getChildAt(i).setEnabled(false);
                //每个item中的控件都需要切换状态
                setEnable(mMainBottomSwitcherContainer.getChildAt(i), false);
            } else {
                //可以点击
//                mMainBottomSwitcherContainer.getChildAt(i).setEnabled(true);
                //每个item中的控件都需要切换状态
                setEnable(mMainBottomSwitcherContainer.getChildAt(i), true);
            }
        }
    }

    /**
     * 将每个item中的所有控件状态一同改变
     * 由于我们处理一个通用的代码，那么item可能会有很多层，所以我们需要使用递归
     *
     * @param item
     * @param b
     */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);
        if (item instanceof ViewGroup) {
            int childCount = ((ViewGroup) item).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnable(((ViewGroup) item).getChildAt(i), b);
            }
        }
    }

    private void changeFragment(int index) {
        //通过这个底部容器item的index能够获取到对应的Fragment，需要将所有的Fragment对号放好(使用集合)
        Fragment fragment = mFragments.get(index);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }
}
