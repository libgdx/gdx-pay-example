package com.badlogic.gdx.pay.app;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class GenericAndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        GdxPayApp game = new GdxPayApp();

        initFlavor(game);

        initialize(game, config);
    }

    protected void initFlavor(GdxPayApp game) {
        // see flavored launcher classes
    }
}
