package com.github.nekdenis.simpledict.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nekdenis.simpledict.R;

public class WordListFragment extends Fragment {

        public WordListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_word_list, container, false);
            return rootView;
        }
    }