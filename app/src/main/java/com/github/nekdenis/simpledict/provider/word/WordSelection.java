package com.github.nekdenis.simpledict.provider.word;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.github.nekdenis.simpledict.provider.base.AbstractSelection;

/**
 * Selection for the {@code word} table.
 */
public class WordSelection extends AbstractSelection<WordSelection> {
    @Override
    public Uri uri() {
        return WordColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code WordCursor} object, which is positioned before the first entry, or null.
     */
    public WordCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new WordCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public WordCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public WordCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public WordSelection id(long... value) {
        addEquals(WordColumns._ID, toObjectArray(value));
        return this;
    }


    public WordSelection id(long... value) {
        addEquals(WordColumns.ID, toObjectArray(value));
        return this;
    }

    public WordSelection idNot(long... value) {
        addNotEquals(WordColumns.ID, toObjectArray(value));
        return this;
    }

    public WordSelection idGt(long value) {
        addGreaterThan(WordColumns.ID, value);
        return this;
    }

    public WordSelection idGtEq(long value) {
        addGreaterThanOrEquals(WordColumns.ID, value);
        return this;
    }

    public WordSelection idLt(long value) {
        addLessThan(WordColumns.ID, value);
        return this;
    }

    public WordSelection idLtEq(long value) {
        addLessThanOrEquals(WordColumns.ID, value);
        return this;
    }

    public WordSelection originalWord(String... value) {
        addEquals(WordColumns.ORIGINAL_WORD, value);
        return this;
    }

    public WordSelection originalWordNot(String... value) {
        addNotEquals(WordColumns.ORIGINAL_WORD, value);
        return this;
    }

    public WordSelection originalWordLike(String... value) {
        addLike(WordColumns.ORIGINAL_WORD, value);
        return this;
    }

    public WordSelection translation(String... value) {
        addEquals(WordColumns.TRANSLATION, value);
        return this;
    }

    public WordSelection translationNot(String... value) {
        addNotEquals(WordColumns.TRANSLATION, value);
        return this;
    }

    public WordSelection translationLike(String... value) {
        addLike(WordColumns.TRANSLATION, value);
        return this;
    }

    public WordSelection source(String... value) {
        addEquals(WordColumns.SOURCE, value);
        return this;
    }

    public WordSelection sourceNot(String... value) {
        addNotEquals(WordColumns.SOURCE, value);
        return this;
    }

    public WordSelection sourceLike(String... value) {
        addLike(WordColumns.SOURCE, value);
        return this;
    }

    public WordSelection addedDate(Long... value) {
        addEquals(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordSelection addedDateNot(Long... value) {
        addNotEquals(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordSelection addedDateGt(long value) {
        addGreaterThan(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordSelection addedDateGtEq(long value) {
        addGreaterThanOrEquals(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordSelection addedDateLt(long value) {
        addLessThan(WordColumns.ADDED_DATE, value);
        return this;
    }

    public WordSelection addedDateLtEq(long value) {
        addLessThanOrEquals(WordColumns.ADDED_DATE, value);
        return this;
    }
}
