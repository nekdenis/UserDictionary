package com.github.nekdenis.simpledict;

import android.app.Application;

import com.androidquery.callback.AjaxCallback;
import com.github.nekdenis.simpledict.util.Consts;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initAndroidQuery();
    }

    private void initAndroidQuery() {
        AjaxCallback.setNetworkLimit(Consts.REQUEST_RETRY_COUNT);
        AjaxCallback.setTimeout(Consts.REQUEST_TIMEOUT);
    }
}
