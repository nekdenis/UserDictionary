package com.github.nekdenis.simpledict.provider.word;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;

import com.github.nekdenis.simpledict.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code word} table.
 */
public class WordContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return WordColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, WordSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public WordContentValues putOriginalWord(String value) {
        if (value == null) throw new IllegalArgumentException("value for originalWord must not be null");
        mContentValues.put(WordColumns.ORIGINAL_WORD, value);
        return this;
    }



    public WordContentValues putTranslation(String value) {
        if (value == null) throw new IllegalArgumentException("value for translation must not be null");
        mContentValues.put(WordColumns.TRANSLATION, value);
        return this;
    }



    public WordContentValues putSource(String value) {
        mContentValues.put(WordColumns.SOURCE, value);
        return this;
    }

    public WordContentValues putSourceNull() {
        mContentValues.putNull(WordColumns.SOURCE);
        return this;
    }


    public WordContentValues putAddedDate(Long value) {
        mContentValues.put(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordContentValues putAddedDateNull() {
        mContentValues.putNull(WordColumns.ADDED_DATE);
        return this;
    }

}
