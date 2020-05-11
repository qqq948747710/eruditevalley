package com.app.erudite.administrator.eruditevalley.View.CycleView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.erudite.administrator.eruditevalley.Model.Cycleinfo;
import com.app.erudite.administrator.eruditevalley.R;

import java.util.ArrayList;
import java.util.List;


public class CycleViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {
    Context mContext;
    private ViewPager mViewPager;//视图容器
    private ViewPagerAdapter mAdapter;//适配器
    private TextView mTitle;
    private LinearLayout mIndication;//指示器布局
    private int mIndicationSelected;//指示器图标
    private int mIndicationUnSelected;
    private ImageView[] mIndicators;//指示器列表
    private Handler handler;//切换处理器
    private int delay=6000;//默认等待时间
    private int mCurrentPosition=0;//轮播图当前位置
    private boolean isWheel=false;//是否轮播
    private boolean isCycle=true;//是否循环
    private long releaseTime=0;// 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private final int WHEEL=100;
    private final int WHEEL_WAIT=101;
    private List<View> mViews=new ArrayList<>();//Image视图集合封装好图片丢人viewpager
    private List<Cycleinfo> infos;
    private ImageCycleViewListener mImageCycleViewListener;//点击回调接口
    private boolean isScrolling;
    final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (mContext!=null&&isWheel){
                long now=System.currentTimeMillis();
                if(now-releaseTime>delay-500){
                    handler.sendEmptyMessage(WHEEL);
                }else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    public CycleViewPager(Context context) {
        this(context, null);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CycleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.cycleview_layout_item,this,true);
        mViewPager = (ViewPager)findViewById(R.id.cycle_view_pager);
        mTitle = (TextView) findViewById(R.id.cycle_title);
        mIndication= (LinearLayout)findViewById(R.id.cycle_indicator);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==WHEEL){
                    if(!isScrolling){
                        int posttion=(mCurrentPosition+1)%mViews.size();
                        mViewPager.setCurrentItem(posttion,true);
                    }
                    releaseTime=System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,delay);
                    return;
                }
                if(msg.what==WHEEL_WAIT&&mViews.size()>0){
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable,delay);
                }
            }
        };
    }
    //首先设置指示器图片
    public void setIndications(int select,int unselect){
        mIndicationSelected = select;
        mIndicationUnSelected=unselect;
    }
    //之后设置容器资料和初始化
    public void setData(List<Cycleinfo> list, ImageCycleViewListener listener){
        setData(list,listener,0);
    }



    public void setData(List<Cycleinfo> list, ImageCycleViewListener listener, int showPosition){
        if(list==null||list.size()==0){
            //this.setVisibility(View.GONE);
            return;
        }
        mViews.clear();
        infos=list;
        if(isCycle){
            mViews.add(getImageView(mContext,infos.get(infos.size()-1).getBp()));
            for(int i=0;i<infos.size();i++){
                mViews.add(getImageView(mContext,infos.get(i).getBp()));
            }
            mViews.add(getImageView(mContext,infos.get(0).getBp()));
        }else {
            for(int i=0;i<infos.size();i++){
                mViews.add(getImageView(mContext,infos.get(i).getBp()));
            }
        }
        if(mViews==null||mViews.size()==0){
            this.setVisibility(View.GONE);
            return;
        }
        mImageCycleViewListener=listener;
        int ivSize=mViews.size();
        //设置指示器
        mIndicators=new ImageView[ivSize];
        if(isCycle)
            mIndicators=new ImageView[ivSize-2];
        mIndication.removeAllViews();
        for(int i=0;i<mIndicators.length;i++){
            mIndicators[i]=new ImageView(mContext);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10,0,10,0);
            mIndicators[i].setLayoutParams(lp);
            mIndication.addView(mIndicators[i]);
        }
        mAdapter=new ViewPagerAdapter();
        setIndicator(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        if(showPosition<0||showPosition>=mViews.size()){
            showPosition=0;
        }
        if (isCycle){
            showPosition=showPosition+1;
        }
        mViewPager.setCurrentItem(showPosition);
        setWheel(true);
    }

    /**
     * 设置指向器样式
     * @param selectedPosition
     */
    private void setIndicator(int selectedPosition) {
        setText(mTitle,infos.get(selectedPosition).getTitle());
        try {
            for (int i=0;i<mIndicators.length;i++){
                mIndicators[i].setBackgroundResource(mIndicationUnSelected);
            }
            if(mIndicators.length>selectedPosition){
                mIndicators[selectedPosition].setBackgroundResource(mIndicationSelected);
            }
        }catch (Exception e){
            Log.i("CycleViewPager","指示器路径错误");
        }
    }

    /**
     * 为TextView设置文本
     * @param textView
     * @param text
     */
    private void setText(TextView textView,String text) {
        if(text!=null&&textView!=null)textView.setText(text);
    }

    //图片视图封装后获取
    private View getImageView(Context context, Bitmap bp) {
        ImageView im=new ImageView(context);
        im.setImageBitmap(bp);
        im.setScaleType(ImageView.ScaleType.FIT_XY);
        return im;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int arg0) {
        int max=mViews.size()-1;
        int position=arg0;
        mCurrentPosition=arg0;
        if(isCycle){
            if(arg0==0){
                mCurrentPosition=max-1;
            }else if(arg0==max){
                mCurrentPosition=1;
            }
            position=mCurrentPosition-1;
        }
        setIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (state == 0) { // viewPager滚动结束

            releaseTime = System.currentTimeMillis();
            //跳转到第mCurrentPosition个页面（没有动画效果，实际效果页面上没变化）
            mViewPager.setCurrentItem(mCurrentPosition, false);
        }
        isScrolling = false;
    }
    public boolean isWheel() {
        return isWheel;
    }

    public void setWheel(boolean wheel) {
        isWheel = wheel;
        isCycle = true;
        if (isWheel) {
            handler.postDelayed(runnable, delay);
        }
    }
    public void refreshData() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
    public static interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param info
         * @param position
         * @param imageView
         */
        public void onImageClick(Cycleinfo info, int position, View imageView);
    }
    private class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v=mViews.get(position);
            if(mImageCycleViewListener!=null){
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImageCycleViewListener.onImageClick(infos.get(mCurrentPosition-1),mCurrentPosition,v);
                    }
                });
            }
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
