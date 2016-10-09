package com.lixx.adv.lib;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by lixx on 2016/10/9.
 */
public class AdvViewAdapter extends PagerAdapter {

    private List<ImageView> mList;

    public AdvViewAdapter(List<ImageView> mList){
        this.mList = mList;
    }
    public void setPagerData(List<ImageView> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        // return super.instantiateItem(container, position);
        // 修改position
        final int mPosition = position % mList.size();
        // 将图片控件添加到容器
        ImageView view = mList.get(mPosition);
        container.addView(view);

        // 返回
        return mList.get(mPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

}
