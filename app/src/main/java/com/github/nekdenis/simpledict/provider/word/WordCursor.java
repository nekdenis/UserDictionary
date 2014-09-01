package com.github.nekdenis.simpledict.provider.word;

import java.util.Date;

import android.database.Cursor;

import com.github.nekdenis.simpledict.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code word} table.
 */
public class WordCursor extends AbstractCursor {
    public WordCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code original_word} value.
     * Cannot be {@code null}.
     */
    public String getOriginalWord() {
        Integer index = getCachedColumnIndexOrThrow(WordColumns.ORIGINAL_WORD);
        return getString(index);
    }

    /**
     * Get the {@code translation} value.
     * Cannot be {@code null}.
     */
    public String getTranslation() {
        Integer index = getCachedColumnIndexOrThrow(WordColumns.TRANSLATION);
        return getString(index);
    }

    /**
     * Get the {@code source} value.
     * Can be {@code null}.
     */
    public String getSource() {
        Integer index = getCachedColumnIndexOrThrow(WordColumns.SOURCE);
        return getString(index);
    }

    /**
     * Get the {@code added_date} value.
     * Can be {@code null}.
     */
    public Long getAddedDate() {
        return getLongOrNull(WordColumns.ADDED_DATE);
    }
}
