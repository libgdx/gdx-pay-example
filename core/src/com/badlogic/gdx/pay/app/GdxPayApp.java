package com.badlogic.gdx.pay.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GdxPayApp extends ApplicationAdapter {
    public static final String REPOLINK = "https://github.com/libgdx/gdx-pay";
    public PurchaseManager purchaseManager;

    Skin skin;
    Stage stage;
    private TextureAtlas atlas;

    @Override
    public void create() {
        stage = new Stage(new ExtendViewport(800, 450));
        Gdx.input.setInputProcessor(stage);

        prepareSkin();

        prepareUI();
    }

    private void prepareUI() {
        Label repoLink = new Label(REPOLINK, skin);
        repoLink.setColor(.3f, .3f, 1f, 1f);
        repoLink.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI(REPOLINK);
            }
        });

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(5);
        stage.addActor(table);

        table.add(new Label("Gdx-Pay Demo App", skin)).colspan(3).padBottom(0);
        table.row();
        table.add(repoLink).padBottom(20).colspan(3);
        table.row();

        if (purchaseManager != null) {
            table.add(new Label("Purchase Manager: " + purchaseManager.storeName(), skin));
            table.row();
            Button openShopButton = new TextButton("Open shop", skin);
            openShopButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    new MyFancyInAppShop(GdxPayApp.this).show(stage);
                }
            });
            table.add(openShopButton);
        } else {
            table.add(new Label("No purchase manager set.", skin));
        }


    }

    private void prepareSkin() {
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but
        // strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();
        atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("skin/uiskin.json"));

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        
        if (purchaseManager != null)
            purchaseManager.dispose();
    }
}
