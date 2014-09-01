package com.github.nekdenis.simpledict.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.github.nekdenis.simpledict.R;
import com.github.nekdenis.simpledict.fragment.WordListFragment;

/**
 *  Activity with @see WordListFragment
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new WordListFragment())
                    .commit();
        }
    }

}
