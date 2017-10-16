package com.first.basket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.first.basket.bean.ProductsBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

/**
 * Created by hanshaobo on 16/10/2017.
 */

public class ProductDao {

    private static final String TABLE = "table_product";
    private static final String ITEM_ROWID = "_id";

    private static final String ITEM_PRODUCT = "product";   //具体
    private static final String ITEM_COUNT = "count";       //个数

    private static final String ITEM_PRODUCTID = "productid";
    private static final String ITEM_PRODUCTNAME = "productname";
    private static final String ITEM_UNIT = "unit";
    private static final String ITEM_PRICE = "price";
    private static final String ITEM_IMG = "img";

//    private static final String OBJECT = "object";

    public static String CREATE_TABLE = "create table " + TABLE + "(" + ITEM_ROWID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + ITEM_PRODUCT + " text," + ITEM_COUNT + " text) ";

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


    public synchronized void insertOrUpdateItem(ProductsBean productsBean) {
        SQLiteDatabase db;
        Cursor cursor = null;
        try {
            Gson gson = new Gson();
            String object = gson.toJson(productsBean);
            long result = -1;
            db = helper.openDatabase();
            cursor = db.query(TABLE, new String[]{ITEM_ROWID}, ITEM_PRODUCT + "=?",
                    new String[]{productsBean.getProductid() + ""}, null, null, null);
            if (cursor.moveToFirst()) {
                result = cursor.getLong(cursor.getColumnIndex(ITEM_ROWID));
            }
            ContentValues values = new ContentValues();
            if (result != -1) {
                //已有
                values.put(ITEM_PRODUCT, object);
//                values.put(ITEM_COUNT, values.getAsInteger(ITEM_COUNT));
//                values.put(ITEM_PRODUCTNAME, productsBean.getProductname());
//                values.put(ITEM_PRODUCTID, productsBean.getProductid());
//                values.put(ITEM_UNIT, productsBean.getProductid());
//                values.put(ITEM_PRICE, productsBean.getPrice());
//                values.put(ITEM_IMG, productsBean.getImg());
                db.update(TABLE, values, ITEM_ROWID + "=?", new String[]{result + ""});
            } else {
                values.put(ITEM_PRODUCT, object);
//                values.put(ITEM_COUNT, values.getAsInteger(ITEM_COUNT));
//                values.put(ITEM_PRODUCTNAME, productsBean.getProductname());
//                values.put(ITEM_PRODUCTID, productsBean.getProductid());
//                values.put(ITEM_UNIT, productsBean.getProductid());
//                values.put(ITEM_PRICE, productsBean.getPrice());
//                values.put(ITEM_IMG, productsBean.getImg());
                db.insert(TABLE, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (helper != null) {
                helper.closeDatabase();
            }
        }
    }


    public ArrayList<ProductsBean> getProducts() {
        SQLiteDatabase db;
        Cursor cursor = null;
        ArrayList<ProductsBean> productsBeans = new ArrayList<ProductsBean>();
        try {
            db = helper.openDatabase();
            String sql = "select * from " + ITEM_PRODUCT;
            cursor = db.rawQuery(sql, new String[]{""});
            ProductsBean productsBean1;
            while (cursor.moveToNext()) {
                productsBean1 = new ProductsBean();
                String object = cursor.getString(cursor.getColumnIndex(ITEM_PRODUCT));
                Gson gson = new Gson();
                try {
                    ProductsBean productsBean = gson.fromJson(object, ProductsBean.class);
                    productsBean.setProductid(productsBean.getProductid());
                    productsBean.setProductname(productsBean.getProductname());
                    productsBean.setUnit(productsBean.getUnit());
                    productsBean.setPrice(productsBean.getPrice());
                    productsBean.setImg(productsBean.getImg());

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

                productsBeans.add(productsBean1);
            }
        } catch (Exception e) {
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
