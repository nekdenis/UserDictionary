package com.github.nekdenis.simpledict.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.nekdenis.simpledict.R;
import com.github.nekdenis.simpledict.provider.word.WordCursor;

/**
 * adapter that shows WordCursor content
 */
public class WordAdapter extends CursorAdapter {

    private final LayoutInflater mInflater;

    public WordAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        WordCursor c = new WordCursor(cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.originalWord.setText(c.getOriginalWord());
        holder.translatedWord.setText(c.getTranslation());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final View view = mInflater.inflate(R.layout.item_dict, parent, false);
        viewHolder.originalWord = (TextView) view.findViewById(R.id.dict_orig_word);
        viewHolder.translatedWord = (TextView) view.findViewById(R.id.dict_trans_word);
        view.setTag(viewHolder);
        return view;
    }

    static class ViewHolder {
        public TextView originalWord;
        public TextView translatedWord;
    }
}