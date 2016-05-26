package com.mansoul.animviewpager.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * viewpager渐变特效特效
 * Created by Mansoul on 16/5/26.
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f; //默认page渐变
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    @Override
    public void transformPage(View view, float position) {
        //position区间 [-Infinity,-1)  [-1,1]  (1,+Infinity]

        if (position < -1) { //左边的page
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1] 滑动过程中position的变化

            if (position < 0) //[0，-1]
            {
                //alpha值 1 -> mMinAlpha
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                //alpha值  mMinAlpha -> 1
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // 右边的page
            view.setAlpha(mMinAlpha);
        }
    }
}
