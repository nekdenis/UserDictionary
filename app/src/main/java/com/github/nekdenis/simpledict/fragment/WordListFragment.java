package com.github.nekdenis.simpledict.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.nekdenis.simpledict.R;
import com.github.nekdenis.simpledict.adapter.WordAdapter;
import com.github.nekdenis.simpledict.provider.word.WordColumns;

public class WordListFragment extends Fragment {

    public static final int WORDS_LOADER_ID = 0;
    private ListView wordList;
    private WordAdapter wordAdapter;
    private EditText newWordEditText;
    private Button newWordButton;

    public WordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoaders();
    }

    private void initLoaders() {
        getActivity().getSupportLoaderManager().initLoader(WORDS_LOADER_ID, null, new WordLoaderCallback()  );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word_list, container, false);
        initList(rootView);
        return rootView;
    }

    private void initList(View rootView) {
        wordAdapter = new WordAdapter(getActivity(), null);
        wordList = (ListView) rootView.findViewById(R.id.word_list_listview);
        wordList.setAdapter(wordAdapter);
    }

    private class WordLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(getActivity(), WordColumns.CONTENT_URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (wordAdapter != null) {
                wordAdapter.swapCursor(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}