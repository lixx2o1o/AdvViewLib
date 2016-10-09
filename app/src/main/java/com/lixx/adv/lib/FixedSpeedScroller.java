package com.lixx.adv.lib;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by lixx on 2016/10/9.
 */
public class FixedSpeedScroller extends Scroller {
    private int mDuration = 2000;
    public FixedSpeedScroller(Context context,int mDuration) {
        super(context);
        this.mDuration = mDuration;
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
