package com.github.nekdenis.simpledict.network;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.github.nekdenis.simpledict.network.parser.Parser;
import com.github.nekdenis.simpledict.util.Consts;
import com.github.nekdenis.simpledict.util.SLog;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for executing GET requests
 */
public class GetAqCommand<T> {

    protected static final String TAG = GetAqCommand.class.getSimpleName();

    protected String methodName;

    protected String requestUrl;

    protected List<NameValuePair> params;

    protected Parser<T> parser;

    public GetAqCommand(String methodName) {
        this.methodName = methodName;
        this.params = new ArrayList<NameValuePair>();
        this.requestUrl = Consts.YANDEX_TRANSLATE_URL + methodName;
    }

    public T execute(AQuery aQuery) throws JSONException {
        String result = null;
        AjaxCallback<String> cb = prepareCallBack();
        aQuery.sync(cb);
        result = cb.getResult();

        SLog.d(TAG, "RESPONSE: " + result.toString());
        return (parser != null) ? parser.parse(result) : null;
    }


    private AjaxCallback<String> prepareCallBack() {
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.url(getUrl()).type(String.class);
        return cb;
    }

    protected String getUrl() {
        String url = requestUrl +"?"+ URLEncodedUtils.format(params, "UTF-8");
        SLog.d(TAG, "REQUEST>>>>:" + url);
        return url;
    }

    protected void addParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }
}
