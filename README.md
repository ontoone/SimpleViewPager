#ViewPager学习

### ViewPager实现无限循环(伪无限循环)

> 在getCount()方法中返回无限大的整数

	public int getCount() {
	            //无线循环
	            return Integer.MAX_VALUE;
	}
	        
	        
> 在instantiateItem()对position进行处理	 
       	        
	public Object instantiateItem() {
			 //使得position的取值在 [0, (data.size-1)]之间
	         position = position % data.size(); //data为数据
	}	        
	
> 最后设置viewpager的位置
	
	//将viewpager的位置设置大概在中间的位置, 使得可以左右循环
	ViewPager setCurrentItem(data.size() * 10000);
完成以上三步, viewpager便实现了无限循环的功能

### ViewPager实现自动轮播

> 定义一个Task类实现Runnable接口

	class Task implements Runnable {

        public void start() {
            //移除以前发送的所有消息, 以免影响现在的消息
            handler.removeCallbacksAndMessages(null); 
           
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
    
> 在ViewPager设置adapter后开启任务

	Task task = new Task();
    task.start();    
    
完成上述二步后, 自动轮播变完成  

### ViewPager切换动画

> 定义ViewPager
 
 核心的2个地方
 
	android:clipChildren="false"
	viewPager.setPageTransformer
	 
在定义xml定义ViewPager是添加属性 
	
	//该属性的意思就是在子View进行绘制时不要去裁切它们的显示范围
	android:clipChildren="false"	
	
注意:要将在ViewPager中展示的图片设置属性
	    
	//设置Page间间距
    mViewPager.setPageMargin(20);
   	//设置缓存的页面数量
    mViewPager.setOffscreenPageLimit(3);
     	
> 定义自己的PageTransformer实现PageTransformer (都是v4包下的)

自定义的PageTransformer只需实现一个方法即可

	public void pageTransform(View view, float position){
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
	
> 来详细解释下上述代码

将position分成三个区间==[-Infinity,-1)==  ==[-1,1]==  ==(1,+Infinity]==

第一个和最后一个区间是当前显示page的左右2边, 设置默认的缩放即可

[-1, 1]为当前page左右切换的区间

第1页->第2页

* 页1的postion变化为：从0到-1

* 页2的postion变化为：从1到0


到这里就完成!








