package com.first.basket.common;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.first.basket.utils.SPUtil;
import com.first.basket.utils.ToastUtil;
import com.first.basket.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hanshaobo on 16/9/13.
 */
public class CommonMethod {

    private static InputMethodManager imm;
    private static Context context = UIUtils.getContext();

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        if (imm == null) {
            imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 弹出软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        if (imm == null) {
            imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.showSoftInput(view, 0);
    }


    /**
     * 匹配手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[0123456789]\\d{8}|17[0123456789]\\d{8}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else {
            if (mobiles.startsWith("86")) mobiles.substring(mobiles.indexOf("86"));
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 匹配邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 匹配密码
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        if (password.length() > 5 && password.length() < 17) {
            return true;
        }
        return false;
    }

    /**
     * 是否有存储卡
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将扫描到的结果转换为匹配vin码，I,O,Q
     *
     * @param str
     * @return
     */
    public static String getVinString(String str) {
        str = str.toUpperCase();
        String regex = "^[A-Z0-9]+$";
        StringBuilder sb = new StringBuilder();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if ((c + "").matches(regex)) {
                c = checkIOQ(c);
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static char checkIOQ(char c) {
        if (c == 'I') {
            c = '1';
        } else if (c == 'O' || c == 'Q') {
            c = '0';
        }
        return c;
    }

    public static String getChemingDir() {
        String path = Environment.getExternalStorageDirectory().getPath();
        if (!path.endsWith("/"))
            path += "/";

        return path + "cheming/";
    }


    public static String makeStrKong(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(chars[i] + " ");
        }
        return sb.toString();
    }

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //获取焦点
    public static void setFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    //失去焦点
    public static void hideFocus(View view) {
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
    }

    /**
     * 根据uri获取路径
     *
     * @param context
     * @param uri
     * @return http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0821/1660.html
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 压缩图片文件
     *
     * @param srcPath
     * @param desPath
     */
    public static void compressPicture(String srcPath, String desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        float hh = 1024f;//
        float ww = 1024f;//
        // 最长宽度或高度1024
        float be = 1.0f;
        if (w > h && w > ww) {
            be = (float) (w / ww);
        } else if (w < h && h > hh) {
            be = (float) (h / hh);
        }
        if (be <= 0) {
            be = 1.0f;
        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


//    public static void hideStatus(BaseActivity activity, View view) {
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = activity.getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = activity.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        view.setPadding(0, ScreenUtils.getStatusHeight(activity), 0, 0);
//    }


//    /**
//     * vin长度是否为0
//     * 是：粘贴
//     * 否：剪切，复制 vin
//     */
//    public static void showClipboard(final Activity activity, final VinInputView vinInputView) {
//        ClipboardManager clipboardManager = null;
//        AlertDialog.Builder dialog = null;
//
//
//        if (clipboardManager == null) {
//            clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
//        }
//        if (dialog == null) {
//            dialog = new AlertDialog.Builder(activity);
//        }
//
//        final String vin = vinInputView.getVerifyCodeStr();
//        if (TextUtils.isEmpty(vin)) {
//            //无输入vin
//            if (clipboardManager.hasPrimaryClip()) {
//                //剪切板有内容
//                final String text = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
//                dialog.setTitle("是否粘贴:");
//                dialog.setMessage(text);
//                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        vinInputView.setVinCode(text);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            } else {
//                SnackbarUtil.showSnackbar(activity, "剪切板无内容");
//            }
//        } else if (vin.length() == 17) {
//            //有输入vin
//            dialog.setTitle("是否复制");
//            dialog.setMessage(vin);
//            final ClipboardManager finalClipboardManager = clipboardManager;
//            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finalClipboardManager.setPrimaryClip(ClipData.newPlainText(null, vin));
//                    dialog.dismiss();
//                    SnackbarUtil.showSnackbar(activity, "已复制到剪切板");
//                }
//            });
//            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        }
//    }

    /**
     * 设置窗体透明度
     *
     * @param activity
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache(true);
    }

    public static Bitmap generate(View view, int width, int height) {

        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    // 保存到sdcard
    public static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
//                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                b.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将手机号中间四位设置为*
     *
     * @param phone
     * @return
     */
    public static String makePhoneStar(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
        return "";
    }

    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLogin() {
        return SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false);
    }


    /**
     * 保存文件到sd
     *
     * @param filename
     * @param content
     * @return
     */
    public static boolean saveContentToSdcard(String filename, String content) {
        boolean flag = false;
        FileOutputStream fos = null;
        try {
            File file = new File(filename, filename);
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
            }
        }
        return flag;
    }

    /**
     * 判断SD卡上的文件是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }


    /**
     * get App verside
     *
     * @return
     */
    public static String getVersionCode() {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


//    public static void copy(Activity activity, VinInputView vinInputView) {
//        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
//        clipboardManager.getPrimaryClip().newPlainText(null, vinInputView.getVerifyCodeStr());
//        SnackbarUtil.showSnackbar(activity, "已复制到剪切板");
//    }
//
//
//    public static void paste(Activity activity, VinInputView vinInputView) {
//        ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clipData = clipboardManager.getPrimaryClip();
//        if (clipData != null) {
//            String text = clipData.getItemAt(0).getText().toString().trim().replace(" ", "");
//            if (vinInputView.getVerifyCodeStr().length() < 17) {
//                vinInputView.insertVinCode(text);
//            }
//        }
//    }


    public static boolean hasPermissionOfWrite(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isTrue(String str) {
        if (TextUtils.isEmpty(str)) return false;
        return str.toLowerCase().equals("y");
    }


    public static void showLogin() {
        ToastUtil.INSTANCE.showToast("请登录");
    }

    public static void replaceStreet(String street) {

    }

    /*
     * 判断给定字符串时间是否为今日
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
}
