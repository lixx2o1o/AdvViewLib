package com.lixx.adv.lib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixx on 2016/10/9.
 */
public class ViewPagerHelper {
    private Context context;
    private ViewPager mViewPager;
    private LinearLayout pointGroup;
    private Handler mHandler = new Handler();
    private List<ImageView> mList = new ArrayList<ImageView>();
    // 图片资源
    private List<Integer> mImages = new ArrayList<Integer>();

    private int pointSelectId = R.drawable.shape_point_selector;
    private AdvViewAdapter mAdvViewAdapter;
    private OnItemClickListener onItemClickListener;
    private boolean isDown =false;
    private float x = 0;
    private float x1 = 0;

    public ViewPagerHelper(Context context, ViewPager mViewPager, LinearLayout pointGroup){
        this.context = context;
        this.mViewPager = mViewPager;
        this.pointGroup = pointGroup;
        mImages= new ArrayList<Integer>();
        mImages.clear();
        mImages.add(R.mipmap.adv1);
        mImages.add(R.mipmap.adv2);
        mImages.add(R.mipmap.adv3);
        mImages.add(R.mipmap.adv4);
        initViewPager();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void initViewPager() {
        // 准备显示的图片集合
        mList = new ArrayList<ImageView>();
        for (int i = 0; i < mImages.size(); i++) {
            ImageView imageView = new ImageView(context);
            // 将图片设置到ImageView控件上
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(mImages.get(i));
            // 将ImageView控件添加到集合
            mList.add(imageView);

            // 制作底部小圆点
            ImageView pointImage = new ImageView(context);
            pointImage.setImageResource(pointSelectId);

            // 设置小圆点的布局参数
            int PointSize = context.getResources().getDimensionPixelSize(R.dimen.point_size);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(PointSize, PointSize);

            if (i > 0) {
                params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.point_margin);
                pointImage.setSelected(false);
            } else {
                pointImage.setSelected(true);
            }
            pointImage.setLayoutParams(params);
            // 添加到容器里
            pointGroup.addView(pointImage);
        }
        mAdvViewAdapter = new AdvViewAdapter(mList);
        mViewPager.setAdapter(mAdvViewAdapter);

        // 对ViewPager设置滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            int lastPosition;
            @Override
            public void onPageSelected(int position) {
                // 页面被选中

                // 修改position
                position = position % mList.size();

                // 设置当前页面选中
                pointGroup.getChildAt(position).setSelected(true);
                // 设置前一页不选中
                pointGroup.getChildAt(lastPosition).setSelected(false);

                // 替换位置
                lastPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentPosition = mViewPager.getCurrentItem();

                if(currentPosition == mViewPager.getAdapter().getCount() - 1){
                    // 最后一页
                    mViewPager.setCurrentItem(0);
                }else{
                    mViewPager.setCurrentItem(currentPosition + 1);
                }
                // 一直给自己发消息
                mHandler.postDelayed(this,2000);
            }
        },2000);
        mViewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        isDown = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(isDown && Math.abs(x - x1)<=20){
                            int position = mViewPager.getCurrentItem()%mList.size();
                            if (onItemClickListener != null) {
                                onItemClickListener.onClick(position);
                            }
                        }
                        isDown = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x1 =  motionEvent.getRawX();
                        break;
                }
                return false;
            }
        });
    }


    public void setPointSelector(int pointSelectId){
        if(pointSelectId>0) {
            this.pointSelectId = pointSelectId;
        }else{
            this.pointSelectId = R.drawable.shape_point_selector;
        }
    }

    public void setImageResource(List<Integer> mImages){
        if(mImages !=null && mImages.size() > 0) {
            this.mImages = mImages;
        }
    }

    public void setPageTransformer(ViewPager.PageTransformer mPageTransformer){
        mViewPager.setPageTransformer(true, mPageTransformer);
    }

    public void setViewPagerScrollSpeed(int mDuration){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ),mDuration);
            mScroller.set( mViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}
