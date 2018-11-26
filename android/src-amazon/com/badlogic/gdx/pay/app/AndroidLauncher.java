package com.badlogic.gdx.pay.app;

import com.badlogic.gdx.pay.android.amazon.PurchaseManagerAndroidAmazon;

/**
 * Created by Benjamin Schulte on 26.11.2018.
 */

public class AndroidLauncher extends GenericAndroidLauncher {
    @Override
    protected void initFlavor(GdxPayApp game) {
        super.initFlavor(game);

        game.purchaseManager = new PurchaseManagerAndroidAmazon(this, 0);
    }
}
