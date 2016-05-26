package com.mansoul.animviewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mansoul.animviewpager.view.AlphaPageTransformer;
import com.mansoul.animviewpager.view.ScaleInPageTransformer;

public class MainActivity extends AppCompatActivity {

    private int[] mImgIds = new int[]{
            R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g}; //viewpager轮播图片

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        //设置page间距
        mViewPager.setPageMargin(20);
        //设置缓存页面的数量
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setAdapter(new MyPagerAdapter());

//        mViewPager.setPageTransformer(true, new AlphaPageTransformer()); //渐变动画
        mViewPager.setPageTransformer(true, new ScaleInPageTransformer()); //缩放动画

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImgIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(mImgIds[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
