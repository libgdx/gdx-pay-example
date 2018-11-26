package com.badlogic.gdx.pay.app.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.pay.app.GdxPayApp;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(800, 600);
        config.preferFlash = false;
        return config;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        GdxPayApp game = new GdxPayApp();
        return game;
    }
}