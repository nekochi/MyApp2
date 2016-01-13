package com.nekomimi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nekomimi.bean.EHentaiMangaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongchi on 2016-1-11.
 * File description :
 */
public class MangaDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MangaReader.db"; //数据库名称
    private static final int version = 1; //数据库版本

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MangaReaderContract.MangaGallery.TABLE_NAME + " (" +
            MangaReaderContract.MangaGallery._ID + " INTEGER PRIMARY KEY," +
            MangaReaderContract.MangaGallery.COLUMN_NAME_GALLERY_ID + INTEGER_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_ARCHIVER_KEY + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE_JPN + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_THUMB + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_UPLOADER +  TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_POSTED + TEXT_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_COUNT + INTEGER_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_SIZE + INTEGER_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_EXPUNGED + INTEGER_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_RATING + REAL_TYPE + COMMA_SEP +
            MangaReaderContract.MangaGallery.COLUMN_NAME_TAGS + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MangaReaderContract.MangaGallery.TABLE_NAME;

    public MangaDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insert(EHentaiMangaInfo mangaInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_GALLERY_ID, mangaInfo.gid);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE, mangaInfo.title);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_ARCHIVER_KEY, mangaInfo.archiver_key);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE_JPN, mangaInfo.title_jpn);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_CATEGORY , mangaInfo.category);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_THUMB, mangaInfo.thumb);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_UPLOADER, mangaInfo.uploader);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_POSTED, mangaInfo.posted);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_COUNT, mangaInfo.filecount);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_SIZE, mangaInfo.filesize);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_EXPUNGED, mangaInfo.expunged);
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_RATING, mangaInfo.rating);
        StringBuilder sb = new StringBuilder();
        for(String i : mangaInfo.tags)
        {
            sb.append(i).append(",");
        }
        values.put(MangaReaderContract.MangaGallery.COLUMN_NAME_TAGS, sb.substring(0, sb.length() - 1));
        return db.insert(MangaReaderContract.MangaGallery.TABLE_NAME, MangaReaderContract.MangaGallery.COLUMN_NAME_NULLABLE, values);
    }

    public List<EHentaiMangaInfo> queryByGid(int gid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectionArgs[] = {String.valueOf(gid)};
        Cursor c = db.query(MangaReaderContract.MangaGallery.TABLE_NAME, null, MangaReaderContract.MangaGallery.COLUMN_NAME_GALLERY_ID + " = ?", selectionArgs, null, null, null);
        if(!c.moveToFirst())
            return null;
        int count = c.getCount();
        List<EHentaiMangaInfo> result = new ArrayList<>(c.getCount());
        for(int i = 0 ; i < count ; i++)
        {
            EHentaiMangaInfo info = new EHentaiMangaInfo();
            info.tags = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_TAGS)).split(",");
            info.archiver_key = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_ARCHIVER_KEY));
            info.category = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_CATEGORY));
            info.expunged = c.getInt(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_EXPUNGED)) != 0;
            info.filecount = c.getInt(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_COUNT));
            info.filesize = c.getInt(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_FILE_SIZE));
            info.gid = c.getInt(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_GALLERY_ID));
            info.posted = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_POSTED));
            info.rating = c.getLong(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_RATING));
            info.thumb = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_THUMB));
            info.title = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE));
            info.title_jpn = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_TITLE_JPN));
            info.uploader = c.getString(c.getColumnIndexOrThrow(MangaReaderContract.MangaGallery.COLUMN_NAME_UPLOADER));
            result.add(i,info);
        }
        return result;
    }

    public void deleteByGid(int gid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = MangaReaderContract.MangaGallery.COLUMN_NAME_GALLERY_ID + " = ?";
        String []whereValue = {Integer.toString(gid)};
        db.delete(MangaReaderContract.MangaGallery.TABLE_NAME, where, whereValue);
    }

    public void update(EHentaiMangaInfo info)
    {
        deleteByGid(info.gid);
        insert(info);
    }
}
