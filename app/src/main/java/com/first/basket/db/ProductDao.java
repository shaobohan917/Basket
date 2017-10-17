package com.first.basket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.first.basket.bean.ProductBean;
import com.first.basket.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by hanshaobo on 16/10/2017.
 */

public class ProductDao {

    private static final String TABLE = "table_product";
    private static final String ITEM_ROWID = "_id";

    private static final String ITEM_PRODUCT = "product";   //具体
    private static final String ITEM_PRODUCTID = "productid";   //具体
    private static final String ITEM_COUNT = "count";       //个数


    public static String CREATE_TABLE = "create table " + TABLE + "(" + ITEM_ROWID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + ITEM_PRODUCTID + " text," + ITEM_PRODUCT + " text," + ITEM_COUNT + " text)";

    private static ProductDao mInstance;
    private final DBHelper helper;


    public ProductDao(Context context) {
        helper = DBHelper.getInstance(context);
    }

    public static ProductDao getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ProductDao.class) {
                if (mInstance == null) {
                    mInstance = new ProductDao(context);
                }
            }
        }
        return mInstance;
    }


    public synchronized void insertOrUpdateItem(ProductBean productsBean) {
        String productid = productsBean.getProductid();

        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            Gson gson = new Gson();
            String object = gson.toJson(productsBean);
            long result = -1;
            db = helper.openDatabase();
            cursor = db.query(TABLE, new String[]{ITEM_ROWID}, ITEM_PRODUCTID + "=?",
                    new String[]{productsBean.getProductid() + ""}, null, null, null);
            if (cursor.moveToFirst()) {
                result = cursor.getLong(cursor.getColumnIndex(ITEM_ROWID));
            }
            ContentValues values = new ContentValues();
            if (result != -1) {
                //已有
                values.put(ITEM_PRODUCT, object);
                db.update(TABLE, values, ITEM_ROWID + "=?", new String[]{result + ""});
                LogUtils.Companion.d("更新成功");
            } else {
                values.put(ITEM_PRODUCT, object);
                values.put(ITEM_PRODUCTID, productsBean.getProductid());
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

    public synchronized void insertOrUpdateItems(LinkedHashMap<ProductBean, Integer> products) {

    }


    public ArrayList<ProductBean> getProducts() {
        SQLiteDatabase db;
        Cursor cursor = null;
        ArrayList<ProductBean> productsBeans = new ArrayList<>();
        try {
            db = helper.openDatabase();
            String sql = "select * from " + TABLE;
            cursor = db.rawQuery(sql, null);

            ProductBean productsBean;
            while (cursor.moveToNext()) {
                productsBean = new ProductBean();
                String object = cursor.getString(cursor.getColumnIndex(ITEM_PRODUCT));
                Gson gson = new Gson();
                try {
                    ProductBean product = gson.fromJson(object, ProductBean.class);
                    productsBean.setProductid(product.getProductid());
                    productsBean.setProductname(product.getProductname());
                    productsBean.setUnit(product.getUnit());
                    productsBean.setPrice(product.getPrice());
                    productsBean.setImg(product.getImg());

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    LogUtils.Companion.d("e:::" + e.getMessage());
                }

                productsBeans.add(productsBean);
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
        return productsBeans;
    }
}
