package com.mansoul.animviewpager.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * viewpage 缩放动画
 * Created by Mansoul on 16/5/26.
 */
public class ScaleInPageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.8f; //默认page缩放的大小
    private float mMinScale = DEFAULT_MIN_SCALE;

    @Override
    public void transformPage(View view, float position) {
        //position区间 [-Infinity,-1)  [-1,1]  (1,+Infinity]

        if (position < -1) { //左边的page
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        } else if (position <= 1) { // [-1,1] 滑动过程中position的变化

            if (position < 0) //[0，-1]
            {
                //scale值 1 -> mMinScale
                float factor = mMinScale + (1 - mMinScale) * (1 + position);
                view.setScaleX(factor);
                view.setScaleY(factor);
            } else//[1，0]
            {
                //scale值  mMinScale -> 1
                float factor = mMinScale + (1 - mMinScale) * (1 - position);
                view.setScaleX(factor);
                view.setScaleY(factor);
            }
        } else { // 右边的page
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }
    }
}
