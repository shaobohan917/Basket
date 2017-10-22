//package com.first.basket.view;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.first.basket.R;
//
///**
// * Created by hanshaobo on 2016/12/6.
// */
//
//public class CameraPopupWindow extends PopupWindow {
//
//    private View view;
//
//    private Button btCamera, btGallery, btCancel;
//    private final TextView tvTitle;
//
//
//    public CameraPopupWindow(Context context, View.OnClickListener itemsOnClick) {
//
//        this.view = LayoutInflater.from(context).inflate(R.layout.vin_choose_pop, null);
//        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        btCamera = (Button) view.findViewById(R.id.btCamera);
//        btGallery = (Button) view.findViewById(R.id.btGallery);
//        btCancel = (Button) view.findViewById(R.id.btCancel);
//        // 取消按钮
//        btCancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // 销毁弹出框
//                dismiss();
//            }
//        });
//        // 设置按钮监听
//        btGallery.setOnClickListener(itemsOnClick);
//        btCamera.setOnClickListener(itemsOnClick);
//
//        // 设置外部可点击
//        this.setOutsideTouchable(true);
//        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        this.view.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = view.findViewById(R.id.llContent).getTop();
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//
//
//    /* 设置弹出窗口特征 */
//        // 设置视图
//        this.setContentView(this.view);
//        // 设置弹出窗体的宽和高
//        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
//        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
//
//        // 设置弹出窗体可点击
//        this.setFocusable(true);
//
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 设置弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//
//        // 设置弹出窗体显示时的动画，从底部向上弹出
//        this.setAnimationStyle(R.style.camera_choose_anim);
//
//    }
//
//    public void setTitle(String title) {
//        tvTitle.setText(title);
//    }
//
//    public void setItem(String str1, String str2) {
//        btCamera.setText(str1);
//        btGallery.setText(str2);
//    }
//}
