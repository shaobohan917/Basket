package com.first.basket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;


public class DBHelper extends SQLiteOpenHelper {

	private AtomicInteger mOpenCounter = new AtomicInteger();
	private static final int VERSION = 18;// 数据库当前版本号
	private static final String DEFAULT_DB_NAME = "east_news_db";
	private static DBHelper mInstance;
	private SQLiteDatabase mDatabase;

	private DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	private DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	private DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	private DBHelper(Context context) {
		this(context, DEFAULT_DB_NAME);
	}

	public static DBHelper getInstance(Context context) {
		if (mInstance == null) {
			synchronized (DBHelper.class) {
				if (mInstance == null) {
					mInstance = new DBHelper(context.getApplicationContext());
				}
			}
		}
		return mInstance;
	}

	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mDatabase = getWritableDatabase();
		}
		return mDatabase;
	}

	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(ProductDao.CREATE_TABLE);
			db.execSQL(ContactDao.CREATE_TABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
