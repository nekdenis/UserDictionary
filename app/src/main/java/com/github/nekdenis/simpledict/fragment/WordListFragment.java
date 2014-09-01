package com.github.nekdenis.simpledict.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nekdenis.simpledict.R;
import com.github.nekdenis.simpledict.adapter.WordAdapter;
import com.github.nekdenis.simpledict.network.AsyncTaskCallback;
import com.github.nekdenis.simpledict.network.GetTranslationAsyncTask;
import com.github.nekdenis.simpledict.provider.word.WordColumns;
import com.github.nekdenis.simpledict.provider.word.WordContentValues;
import com.github.nekdenis.simpledict.provider.word.WordSelection;
import com.github.nekdenis.simpledict.util.ConnectionStatus;

public class WordListFragment extends Fragment {

    public static final int WORDS_LOADER_ID = 0;
    private ListView wordList;
    private WordAdapter wordAdapter;
    private EditText newWordEditText;
    private Button newWordButton;
    private GetTranslationAsyncTask translationAsyncTask;
    private String currentFilter = "";

    public WordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onResume() {
        super.onResume();
        //getLoaderManager().restartLoader(WORDS_LOADER_ID, null, new WordLoaderCallback());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentFilter = !TextUtils.isEmpty(newText) ? newText : "";
                getLoaderManager().restartLoader(WORDS_LOADER_ID, null, new WordLoaderCallback());
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                currentFilter = "";
                getLoaderManager().restartLoader(WORDS_LOADER_ID, null, new WordLoaderCallback());
                return true;
            }
        });
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
                onTranslateClick();
            }
        });
        newWordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onTranslateClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void onTranslateClick() {
        if (validateInput()) {
            translate(newWordEditText.getText().toString());
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(newWordEditText.getWindowToken(), 0);
            newWordEditText.setText("");
        }
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
            if (!TextUtils.isEmpty(currentFilter)) {
                WordSelection wordSelection = new WordSelection().originalWordLike("%" + currentFilter + "%").or().translationLike("%" + currentFilter + "%");
                return new CursorLoader(getActivity(), WordColumns.CONTENT_URI, null, wordSelection.sel(), wordSelection.args(), WordColumns.ADDED_DATE + " DESC");
            }
            return new CursorLoader(getActivity(), WordColumns.CONTENT_URI, null, null, null, WordColumns.ADDED_DATE + " DESC");
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