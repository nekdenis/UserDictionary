package com.github.nekdenis.simpledict.network.parser;

import org.json.JSONException;


/**
 * base interface for network JSON response parsers
 */
public interface Parser<T> {
    T parse(String response) throws JSONException;
}
