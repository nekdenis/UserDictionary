package com.github.nekdenis.simpledict.network;


/**
 * base interface for background callbacks that should be executed in UI
 */
public interface AsyncTaskCallback<T> {
    void onPostExecute(T result);
}
