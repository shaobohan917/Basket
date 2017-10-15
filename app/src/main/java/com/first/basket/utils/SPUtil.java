package com.first.basket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.first.basket.common.StaticValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Luka on 2016/3/29.
 * E-mail:397308937@qq.com
 */
public class SPUtil {
    private final Context context;
    private static SharedPreferences sPrefs;

    private SPUtil(Context context) {
        this.context = context;
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void init(Context context) {
        new SPUtil(context);
    }

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void setData(Context context, String key, Object data) {
        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //defValue为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }


    public static String getString(String key, String defaultValue) {
        if (sPrefs == null)
            return null;
        return sPrefs.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        if (sPrefs == null)
            return;
        sPrefs.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        if (sPrefs == null)
            return false;
        return sPrefs.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        if (sPrefs == null)
            return;
        sPrefs.edit().putBoolean(key, value).commit();
    }

    public static Long getLong(String key, long defaultValue) {
        if (sPrefs == null)
            return null;
        return sPrefs.getLong(key, defaultValue);
    }

    public static void setLong(String key, long value) {
        if (sPrefs == null)
            return;
        sPrefs.edit().putLong(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        if (sPrefs == null)
            return 0;
        return sPrefs.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        if (sPrefs == null)
            return;
        sPrefs.edit().putInt(key, value).commit();
    }


    public static void clear() {
        SPUtil.setBoolean(StaticValue.SP_LOGIN_STATUS, false);
        SPUtil.setString(StaticValue.SP_LOGIN_PHONE, "");
        SPUtil.setString(StaticValue.SP_LOGIN_USERNAME, "");
        SPUtil.setString(StaticValue.SP_NICKNAME, "");
        SPUtil.setBoolean(StaticValue.REPORT_ISLOAD, false);
        SPUtil.setString(StaticValue.ACCESS_TOKEN, "");

        //清空微信
        SPUtil.setString(StaticValue.ACCESS_TOKEN_WECHAT, "");
        SPUtil.setString(StaticValue.REFRESH_TOKEN_WECHAT, "");
        SPUtil.setString(StaticValue.OPEN_ID_WECHAT, "");
    }


    /**
     * 储存复杂的数据字段对象
     *
     * @param context
     * @param key
     * @param t
     * @return
     */
    public static <T> boolean saveObjectToShare(Context context, String key, T t) {
        return saveObjectToShare(context, "eastnews", key, t);
    }

    /**
     * @param context
     * @param name
     * @param key
     * @param t
     * @return
     */

    public static <T> boolean saveObjectToShare(Context context, String name, String key, T t) {
        try {
            SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            // 存储
            SharedPreferences.Editor editor = sp.edit();
            if (t == null) {
                editor.putString(key, "");
                editor.commit();
                return true;
            }
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos;

            oos = new ObjectOutputStream(toByte);
            oos.writeObject(t);
            // 对byte[]进行Base64编码
            String payCityMapBase64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));

            editor.putString(key, payCityMapBase64);
            editor.commit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}