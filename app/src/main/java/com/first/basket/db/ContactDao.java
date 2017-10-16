package com.first.basket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.first.basket.bean.ProductsBean;
import com.first.basket.bean.ContactBean;
import com.first.basket.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

/**
 * Created by hanshaobo on 16/10/2017.
 */

public class ContactDao {

    private static final String TABLE = "table_contact";
    private static final String ITEM_ROWID = "_id";

    private static final String ITEM_CONTACT = "contact";   //具体
    private static final String ITEM_CONTACT_ID = "user_id";

    public static String CREATE_TABLE = "create table " + TABLE + "(" + ITEM_ROWID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + ITEM_CONTACT + " text," + ITEM_CONTACT_ID + " text)";

    private static ContactDao mInstance;
    private final DBHelper helper;


    public ContactDao(Context context) {
        helper = DBHelper.getInstance(context);
    }

    public static ContactDao getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ContactDao.class) {
                if (mInstance == null) {
                    mInstance = new ContactDao(context);
                }
            }
        }
        return mInstance;
    }


    public synchronized void insertOrUpdateItem(ContactBean contactBean) {
        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            Gson gson = new Gson();
            String object = gson.toJson(contactBean);
            long result = -1;
            db = helper.openDatabase();
            cursor = db.query(TABLE, new String[]{ITEM_ROWID}, ITEM_CONTACT_ID + "=?",
                    new String[]{contactBean.getUserid() + ""}, null, null, null);
            if (cursor.moveToFirst()) {
                result = cursor.getLong(cursor.getColumnIndex(ITEM_ROWID));
            }
            ContentValues values = new ContentValues();
            if (result != -1) {
                //已有
                values.put(ITEM_CONTACT, object);
                db.update(TABLE, values, ITEM_ROWID + "=?", new String[]{result + ""});
                LogUtils.Companion.d("更新成功");
            } else {
                values.put(ITEM_CONTACT, object);
                values.put(ITEM_CONTACT_ID, contactBean.getUserid());
                db.insert(TABLE, null, values);
                LogUtils.Companion.d("插入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.Companion.d("e:" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (helper != null) {
                helper.closeDatabase();
            }
        }
    }


    public ArrayList<ContactBean> getContacts() {
        SQLiteDatabase db;
        Cursor cursor = null;
        ArrayList<ContactBean> contactBeans = new ArrayList<>();
        try {
            db = helper.openDatabase();
//            String sql = "select * from " + TABLE + " where " + ITEM_CONTACT_ID + " = ?";
            String sql = "select * from " + TABLE;
//            cursor = db.rawQuery(sql, new String[]{"123"});
            cursor = db.rawQuery(sql,null);

            ContactBean contactBean;
            while (cursor.moveToNext()) {
                contactBean = new ContactBean();
                String object = cursor.getString(cursor.getColumnIndex(ITEM_CONTACT));
                Gson gson = new Gson();
                try {
                    ContactBean contactBean1 = gson.fromJson(object, ContactBean.class);
                    contactBean.setUserid(contactBean1.getUserid());
                    contactBean.setPhone(contactBean1.getPhone());
                    contactBean.setUsername(contactBean1.getUsername());
                    contactBean.setAddress(contactBean1.getAddress());

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    LogUtils.Companion.d("e:::" + e.getMessage());
                }

                contactBeans.add(contactBean);
            }
        } catch (Exception e) {
            LogUtils.Companion.d("e123:::" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (helper != null) {
                helper.closeDatabase();
            }
        }
        return contactBeans;
    }
}
