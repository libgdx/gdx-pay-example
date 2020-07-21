package com.badlogic.gdx.pay.app;

import com.badlogic.gdx.pay.android.huawei.HuaweiPurchaseManager;

/**
 * Created by Francesco Stranieri on 11.05.2020.
 */

public class AndroidLauncher extends GenericAndroidLauncher {

    @Override
    protected void initFlavor(GdxPayApp game) {
        super.initFlavor(game);

        game.purchaseManager = new HuaweiPurchaseManager(this);
    }
}
