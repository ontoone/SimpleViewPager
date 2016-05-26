package com.mansoul.animviewpager;

import android.os.Bundle;
import android.os.Handler;
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
    private Handler handler = new Handler();


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

        mViewPager.setCurrentItem(mImgIds.length * 100000);

        //开启轮播任务
        Task task = new Task();
        task.start();

//        mViewPager.setPageTransformer(true, new AlphaPageTransformer()); //渐变动画
        mViewPager.setPageTransformer(true, new ScaleInPageTransformer()); //缩放动画


    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mImgIds.length;

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

    //定时任务, 实现自动轮播
    class Task implements Runnable {

        public void start() {
            handler.removeCallbacksAndMessages(null); //移除以前发送的所有消息, 以免影响现在的消息
            handler.postDelayed(this, 3000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            //继续发送延时3s消息, 实现内循环
            handler.postDelayed(this, 3000);
        }
    }

}
