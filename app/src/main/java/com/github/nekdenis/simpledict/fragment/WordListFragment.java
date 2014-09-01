package com.github.nekdenis.simpledict.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nekdenis.simpledict.R;
import com.github.nekdenis.simpledict.adapter.WordAdapter;
import com.github.nekdenis.simpledict.network.AsyncTaskCallback;
import com.github.nekdenis.simpledict.network.GetTranslationAsyncTask;
import com.github.nekdenis.simpledict.provider.word.WordColumns;
import com.github.nekdenis.simpledict.provider.word.WordContentValues;
import com.github.nekdenis.simpledict.util.ConnectionStatus;

public class WordListFragment extends Fragment {

    public static final int WORDS_LOADER_ID = 0;
    private ListView wordList;
    private WordAdapter wordAdapter;
    private EditText newWordEditText;
    private Button newWordButton;
    private GetTranslationAsyncTask translationAsyncTask;

    public WordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoaders();
    }

    private void initLoaders() {
        getActivity().getSupportLoaderManager().initLoader(WORDS_LOADER_ID, null, new WordLoaderCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word_list, container, false);
        initList(rootView);
        initView(rootView);
        initListeners();
        return rootView;
    }

    @Override
    public void onDetach() {
        detachCallbacks();
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachCallbacks();
    }

    private void initList(View rootView) {
        wordAdapter = new WordAdapter(getActivity(), null);
        wordList = (ListView) rootView.findViewById(R.id.word_list_listview);
        wordList.setAdapter(wordAdapter);
    }

    private void initView(View rootView) {
        newWordEditText = (EditText) rootView.findViewById(R.id.word_list_new_word_edittext);
        newWordButton = (Button) rootView.findViewById(R.id.word_list_new_word_button);
    }

    private void initListeners() {
        newWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    translate(newWordEditText.getText().toString());
                }
            }
        });
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(newWordEditText.getText().toString())) {
            Toast.makeText(getActivity(), R.string.check_input_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void translate(String text) {
        if (!ConnectionStatus.checkInternetConnection(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_internet_toast, Toast.LENGTH_SHORT).show();
            return;
        }
        translationAsyncTask = new GetTranslationAsyncTask(text, getActivity());
        translationAsyncTask.execute();
        translationAsyncTask.attachCallback(new TranslateCallback());
    }

    private void detachCallbacks() {
        if (translationAsyncTask != null) {
            translationAsyncTask.detachCallback();
        }
    }

    private class TranslateCallback implements AsyncTaskCallback<WordContentValues> {
        @Override
        public void onPostExecute(WordContentValues result) {
            result.insert(getActivity().getContentResolver());
            translationAsyncTask = null;
        }
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