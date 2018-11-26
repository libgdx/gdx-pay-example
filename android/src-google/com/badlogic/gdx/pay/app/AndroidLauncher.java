package com.badlogic.gdx.pay.app;

import com.badlogic.gdx.pay.android.googlebilling.PurchaseManagerGoogleBilling;

/**
 * Created by Benjamin Schulte on 26.11.2018.
 */

public class AndroidLauncher extends GenericAndroidLauncher {
    @Override
    protected void initFlavor(GdxPayApp game) {
        super.initFlavor(game);
        game.purchaseManager = new PurchaseManagerGoogleBilling(this);
    }
}
