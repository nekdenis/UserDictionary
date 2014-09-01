package com.github.nekdenis.simpledict.provider.word;

import java.util.HashSet;
import java.util.Set;

import android.net.Uri;
import android.provider.BaseColumns;

import com.github.nekdenis.simpledict.provider.GeneratedProvider;

/**
 * Columns for the {@code word} table.
 */
public class WordColumns implements BaseColumns {
    public static final String TABLE_NAME = "word";
    public static final Uri CONTENT_URI = Uri.parse(GeneratedProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;
    public static final String ORIGINAL_WORD = "original_word";
    public static final String TRANSLATION = "translation";
    public static final String SOURCE = "source";
    public static final String ADDED_DATE = "added_date";

    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] FULL_PROJECTION = new String[] {
            TABLE_NAME + "." + _ID + " AS " + BaseColumns._ID,
            TABLE_NAME + "." + ORIGINAL_WORD,
            TABLE_NAME + "." + TRANSLATION,
            TABLE_NAME + "." + SOURCE,
            TABLE_NAME + "." + ADDED_DATE
    };
    // @formatter:on

    private static final Set<String> ALL_COLUMNS = new HashSet<String>();
    static {
        ALL_COLUMNS.add(_ID);
        ALL_COLUMNS.add(ORIGINAL_WORD);
        ALL_COLUMNS.add(TRANSLATION);
        ALL_COLUMNS.add(SOURCE);
        ALL_COLUMNS.add(ADDED_DATE);
    }

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (ALL_COLUMNS.contains(c)) return true;
        }
        return false;
    }
}
