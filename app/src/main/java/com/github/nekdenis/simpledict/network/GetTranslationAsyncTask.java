package com.github.nekdenis.simpledict.network;

import android.content.Context;
import android.os.AsyncTask;

import com.androidquery.AQuery;
import com.github.nekdenis.simpledict.network.parser.TranslationParser;
import com.github.nekdenis.simpledict.provider.word.WordContentValues;
import com.github.nekdenis.simpledict.util.Consts;
import com.github.nekdenis.simpledict.util.SLog;

import org.json.JSONException;

public class GetTranslationAsyncTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = GetTranslationAsyncTask.class.getSimpleName();
    private String text;
    private AQuery aQuery;
    private AsyncTaskCallback<WordContentValues> callback;

    public GetTranslationAsyncTask(String text, Context context) {
        this.text = text;
        aQuery = new AQuery(context);
    }

    public void attachCallback(AsyncTaskCallback<WordContentValues> callback) {
        this.callback = callback;
    }

    public void detachCallback() {
        this.callback = null;
    }

    @Override
    protected String doInBackground(Void... params) {
        GetAqCommand<String> translateCommand = new GetAqCommand<String>("translate");
        translateCommand.addParam("key", Consts.YANDEX_KEY);
        translateCommand.addParam("text", text);
        translateCommand.addParam("lang", "en-ru");
        translateCommand.addParam("format", "plain");
        translateCommand.parser = new TranslationParser();
        try {
            return translateCommand.execute(aQuery);
        } catch (JSONException e) {
            SLog.d(TAG, e.getMessage());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String translation) {
        if (callback != null) {
            WordContentValues wordContentValues = new WordContentValues()
                    .putOriginalWord(text)
                    .putAddedDate(System.currentTimeMillis())
                    .putSource("Yandex")
                    .putTranslation(translation);
            callback.onPostExecute(wordContentValues);
        }
    }
}