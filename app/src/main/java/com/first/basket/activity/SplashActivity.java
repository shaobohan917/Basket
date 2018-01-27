package com.first.basket.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.first.basket.R;
import com.first.basket.adapter.ViewPagerAdapter;
import com.first.basket.base.BaseActivity;
import com.first.basket.common.StaticValue;
import com.first.basket.rxjava.RxjavaUtil;
import com.first.basket.rxjava.UITask;
import com.first.basket.utils.SPUtil;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/8.
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.vpGuide)
    ViewPager vpGuide;
    @BindView(R.id.ibGo)
    ImageButton ibGo;
    @BindView(R.id.llDots)
    LinearLayout llDots;
    @BindView(R.id.vPoint)
    View vPoint;

    private int[] images = {R.mipmap.guideimage_1,R.mipmap.guideimage_2};

    private ArrayList<View> imageviews;      //存放imageview的集合
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (SPUtil.getBoolean(StaticValue.SP_FIRST_LAUNCHER, true)) {
            initView();
            initData();
            initListener();
        } else {
            myStartActivity(MainActivity.class, true);
        }
    }

    private void initView() {
        imageviews = new ArrayList<>();
        //为引导图提供布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //初始化引导图列表
        for (int i = 0; i < images.length; i++) {
            //添加vp的图片
            ImageView iv = new ImageView(SplashActivity.this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(images[i]);
            imageviews.add(iv);
            //添加导航点
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(10, 0, 10, 0);
            ImageView ivDot = new ImageView(SplashActivity.this);
            ivDot.setImageResource(R.drawable.guide_dot_normal);
            ivDot.setLayoutParams(params1);
            llDots.addView(ivDot);
        }

        vPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                distance = llDots.getChildAt(1).getLeft() - llDots.getChildAt(0).getLeft();
            }
        });
    }

    private void initData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(imageviews);
        vpGuide.setAdapter(adapter);
        vpGuide.setPageTransformer(true, new ZoomOutSlideTransformer());
    }


    private void initListener() {
        ibGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.setBoolean(StaticValue.SP_FIRST_LAUNCHER, false);
                myStartActivity(MainActivity.class, true);
            }
        });

        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //设置滑动点
                float leftMargin = distance * (position + positionOffset) + 10;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vPoint.getLayoutParams();
                params.leftMargin = Math.round(leftMargin);
                vPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == imageviews.size() - 1) {
                    //显示下一步按钮
                    RxjavaUtil.doInUIThreadDelay(new UITask<Object>() {
                        @Override
                        public void doInUIThread() {
                            ibGo.setVisibility(View.VISIBLE);
                        }
                    }, 300, TimeUnit.MILLISECONDS);
                } else {
                    ibGo.setVisibility(View.GONE);
                }
                for (int i = 0; i < llDots.getChildCount(); i++) {
                    llDots.getChildAt(i).setSelected(i == position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
