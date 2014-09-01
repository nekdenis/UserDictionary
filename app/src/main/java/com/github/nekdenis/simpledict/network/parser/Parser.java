package com.github.nekdenis.simpledict.network.parser;

import org.json.JSONException;

public interface Parser<T> {
    T parse(String response) throws JSONException;
}
