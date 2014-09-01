package com.github.nekdenis.simpledict.network.parser;

import com.github.nekdenis.simpledict.exception.ServerException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Yandex API translation parser
 */
public class TranslationParser implements Parser<String> {
    @Override
    public String parse(String response) throws JSONException {
        JSONObject responseJson = new JSONObject(response);
        int code = responseJson.optInt("code");
        if (code != 200) {
            throw new ServerException("error code = " + code);
        } else {
            StringBuilder resultString = new StringBuilder();
            //collect all words in one string
            JSONArray translations = responseJson.optJSONArray("text");
            for (int i = 0; i < translations.length(); i++) {
                resultString.append(translations.optString(i));
                if (i != translations.length() - 1) {
                    resultString.append(", ");
                }
            }
            return resultString.toString();
        }
    }
}
