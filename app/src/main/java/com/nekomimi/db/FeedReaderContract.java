package com.nekomimi.db;

import android.provider.BaseColumns;

/**
 * Created by hongchi on 2016-1-8.
 * File description :
 */
public final class FeedReaderContract
{
    public FeedReaderContract(){}

    public static abstract class FeedGallery implements BaseColumns
    {
        public static final String TABLE_NAME = "gallery";
        public static final String COLUMN_NAME_GALLERY_ID = "gid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ARCHIVER_KEY = "archiver_key";
        public static final String COLUMN_NAME_TITLE_JPN = "title_jpn";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_THUMB = "thumb";
        public static final String COLUMN_NAME_UPLOADER = "uploader";
        public static final String COLUMN_NAME_POSTED = "posted";
        public static final String COLUMN_NAME_FILE_COUNT = "file_count";
        public static final String COLUMN_NAME_FILE_SIZE = "file_size";
        public static final String COLUMN_NAME_EXPUNGED = "expunged";
        public static final String COLUMN_NAME_RATING = "rating";
    }
}
