package com.qun.takeout.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qun.takeout.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Qun on 2017/6/18.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //位置信息
    //1、多少种item类型(3种)
    //2、position对应数据展示
    //   position:0                   复杂的item
    //   position:1到附近商家集合的size 附近商家
    //   position:size+1              第一个分割线(附近商家和普通商家)
    //   假设其他商家每隔20添加一个分割线
    //   position:size+2              第一组普通商家开始的位置
    //   position:size+2+20           第二个分割线位置
    //   position:size+2+20+1         第二组普通商家开始的位置
    //   position:size+2+20+1+20      第三个分割线位置

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_DIVISION = 1;
    private static final int TYPE_SELLER = 2;
    private int mOtherSellerPerGroupNum = 20;

    private List<String> mNearBySellers;
    private List<String> mOtherSellers;

    public HomeRecyclerViewAdapter(List<String> nearBySellers, List<String> otherSellers) {
        this.mNearBySellers = nearBySellers;
        this.mOtherSellers = otherSellers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                View titleView = View.inflate(parent.getContext(), R.layout.item_home_common, null);
                TitleHolder titleHolder = new TitleHolder(titleView);
                titleHolder.tv.setText("头部数据");
                return titleHolder;
            case TYPE_DIVISION:
                View divisionView = View.inflate(parent.getContext(), R.layout.item_home_common, null);
                DivisionHolder divisionHolder = new DivisionHolder(divisionView);
                divisionHolder.tv.setText("分割线---");
                return divisionHolder;
            case TYPE_SELLER:
                View SellerView = View.inflate(parent.getContext(), R.layout.item_home_common, null);
                SellerHolder sellerHolder = new SellerHolder(SellerView);
                sellerHolder.tv.setText("商家");
                return sellerHolder;
            default:
                break;
        }
        return null;
    }

    private int divisionSum = 0;
    private HashMap<Integer, Integer> positionToIndex = new HashMap<>();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_TITLE:
                break;
            case TYPE_DIVISION:
                divisionSum++;
                break;
            case TYPE_SELLER:
                //商家：附近商家和其他商家
                //依据position判断是从附近商家集合获取数据还是其他商家集合获取数据
                //1到附近商家集合的size
                if (position - 1 < mNearBySellers.size()) {
                    //mNearBySellers有5个数据 index:0-4
                    int index = position - 1;
                    ((SellerHolder) holder).tv.setText(mNearBySellers.get(index));
                } else {
                    //从其他商家集合中获取数据，position与集合的index对应关系
                    //分割线的数量

                    //   position:size+2              第一组普通商家开始的位置
//                    int index = position - mNearBySellers.size() - divisionSum;
//                    ((SellerHolder) holder).tv.setText(mOtherSellers.get(index));

                    //判断：如果positionToIndex中能够通过position获取到index信息，直接到其他商家集合获取数据
                    //获取不到，计算index，并建立position与index的对应关系
                    int index = 0;
                    if (positionToIndex.containsKey(position)) {
                        index = positionToIndex.get(position);
                    } else {
                        index = position - mNearBySellers.size() - 1 - divisionSum;
                        positionToIndex.put(position, index);
                    }
                    ((SellerHolder) holder).tv.setText(mOtherSellers.get(index));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mNearBySellers == null && mOtherSellers == null) {
            return 0;
        }
        //如果附近商家或其他商家有一个不为null，可以添加头信息
        int count = 1;
        if (mNearBySellers != null) {
            count += mNearBySellers.size();
        }
        if (mOtherSellers != null) {
            count += 1;//第一个分割线
            //20-39 +1
            //40-59 +2
            count += mOtherSellers.size();
            count += mOtherSellers.size() / mOtherSellerPerGroupNum;//其余分割线
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (position == 0) {
            type = TYPE_TITLE;
        } else if (position == mNearBySellers.size() + 1) {
            type = TYPE_DIVISION;
        } else if ((position - (mNearBySellers.size() + 1)) % (mOtherSellerPerGroupNum + 1) == 0) {
            type = TYPE_DIVISION;
        } else {
            type = TYPE_SELLER;
        }
        return type;
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public TitleHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class DivisionHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public DivisionHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class SellerHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public SellerHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
