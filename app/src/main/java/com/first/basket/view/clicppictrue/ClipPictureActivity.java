package com.first.basket.view.clicppictrue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.first.basket.R;
import com.first.basket.common.CommonMethod;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClipPictureActivity extends Activity implements
        OnClickListener {
    @BindView(R.id.rlContainer)
    RelativeLayout rlContainer;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    private ClipView clipview;

    private Matrix matrix = new Matrix();

    private static final String FILE_PATH = CommonMethod.getBasketDir() + "ocr/" + "temp_cropped.jpg";

    private Bitmap bitmap;
    private String path;
    private TouchImageView touchImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicp);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("path");
        initRotateView();

        ViewTreeObserver observer = touchImageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                touchImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initClipView(touchImageView.getTop());
            }
        });
        tvConfirm.setOnClickListener(this);
    }

    /**
     * 初始化旋转区域
     */
    private void initRotateView() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        touchImageView = new TouchImageView(this, path);
        rlContainer.addView(touchImageView, layoutParams);
    }

    /**
     * 初始化截图区域，并将源图按裁剪框比例缩放
     *
     * @param top
     */
    private void initClipView(int top) {
        //获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
        int degree = CommonMethod.readPictureDegree(path);

        Bitmap cbitmap = BitmapFactory.decodeFile(path);
        //把图片旋转为正的方向
        Bitmap newbitmap = CommonMethod.rotaingImageView(degree, cbitmap);
        bitmap = newbitmap;

        clipview = new ClipView(ClipPictureActivity.this);
        clipview.setCustomTopBarHeight(top);
        clipview.addOnDrawCompleteListener(new ClipView.OnDrawListenerComplete() {

            public void onDrawCompelete() {
                clipview.removeOnDrawCompleteListener();
                int clipHeight = clipview.getClipHeight();
                int clipWidth = clipview.getClipWidth();
                int midX = clipview.getClipLeftMargin() + (clipWidth / 2);
                int midY = clipview.getClipTopMargin() + (clipHeight / 2);

                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                // 按裁剪框求缩放比例
                float scale = (clipWidth * 1.0f) / imageWidth;
                if (imageWidth > imageHeight) {
                    scale = (clipHeight * 1.0f) / imageHeight;
                }

                // 起始中心点
                float imageMidX = imageWidth * scale / 2;
                float imageMidY = clipview.getCustomTopBarHeight()
                        + imageHeight * scale / 2;
                touchImageView.setScaleType(ScaleType.MATRIX);

                // 缩放
                matrix.postScale(scale, scale);
                // 平移
                matrix.postTranslate(midX - imageMidX, midY - imageMidY);

                touchImageView.setImageMatrix(matrix);
                touchImageView.setImageBitmap(bitmap);
            }
        });

        this.addContentView(clipview, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void onClick(View v) {
        Bitmap clipBitmap = getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        clipBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();

        saveBitmapFile(clipBitmap);
        Intent intent1 = getIntent();
        intent1.putExtra("bitmap", bitmapByte);
        setResult(RESULT_OK, intent1);
        finish();
    }

    //Bitmap对象保存为图片文件
    public void saveBitmapFile(Bitmap bitmap) {
        File file = new File(FILE_PATH);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取裁剪框内截图
     *
     * @return
     */
    private Bitmap getBitmap() {
        // 获取截屏
        View view = this.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Bitmap finalBitmap = Bitmap.createBitmap(view.getDrawingCache(),
                clipview.getClipLeftMargin(), clipview.getClipTopMargin()
                        + statusBarHeight, clipview.getClipWidth(),
                clipview.getClipHeight());

        // 释放资源
        view.destroyDrawingCache();
        return finalBitmap;
    }

}