package com.github.nekdenis.simpledict.network;

public interface AsyncTaskCallback<T> {
    void onPostExecute(T result);
}
