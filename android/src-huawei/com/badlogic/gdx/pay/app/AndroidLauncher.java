package com.badlogic.gdx.pay.app;

import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.pay.huawei.HuaweiPurchaseManager;
import com.badlogic.gdx.pay.huawei.IAPListener;
import com.huawei.hms.iap.Iap;
import com.huawei.hms.iap.IapApiException;
import com.huawei.hms.iap.IapClient;
import com.huawei.hms.iap.entity.ConsumeOwnedPurchaseResult;
import com.huawei.hms.iap.entity.InAppPurchaseData;
import com.huawei.hms.iap.entity.OrderStatusCode;
import com.huawei.hms.iap.entity.PurchaseIntentResult;
import com.huawei.hms.iap.entity.PurchaseResultInfo;

import org.json.JSONException;

/**
 * Created by Francesco Stranieri on 11.05.2020.
 */

public class AndroidLauncher extends GenericAndroidLauncher implements IAPListener {

    private final int PURCHASE_STATUS_RESULT_CODE = 7265;

    GdxPayApp game;

    @Override
    protected void initFlavor(GdxPayApp game) {
        this.game = game;
        super.initFlavor(this.game);

        this.game.purchaseManager = new HuaweiPurchaseManager(this, this);
    }

    @Override
    public void onRegionNotSupported() {
        Gdx.app.log("HUAWEI", "REGION NOT SUPPORTED");
    }

    @Override
    public void onLoginRequired() {
        Gdx.app.log("HUAWEI", "LOGIN REQUESTED");
    }

    @Override
    public void onIAPError(IapApiException exception) {
        Gdx.app.error("HUAWEI", "IAP EXC", exception);
    }

    @Override
    public void onError(Exception exception) {
        Gdx.app.error("HUAWEI", "IAP ERR", exception);
    }

    @Override
    public void onPurchaseResult(PurchaseIntentResult result) {
        try {
            result.getStatus().startResolutionForResult(this, this.PURCHASE_STATUS_RESULT_CODE);
        } catch (IntentSender.SendIntentException e) {
        }
    }

    @Override
    public void onConsumedResult(ConsumeOwnedPurchaseResult result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PURCHASE_STATUS_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        Log.e("onActivityResult", "data is null");
                        return;
                    }
                    PurchaseResultInfo purchaseResultInfo = Iap.getIapClient(this).parsePurchaseResultInfoFromIntent(data);
                    switch (purchaseResultInfo.getReturnCode()) {
                        case OrderStatusCode.ORDER_STATE_CANCEL:
                            // User cancel payment.
                            Gdx.app.log("ORDER_STATE", "CANCEL");
                            break;
                        case OrderStatusCode.ORDER_STATE_FAILED:
                        case OrderStatusCode.ORDER_PRODUCT_OWNED:
                            // to check if there exists undelivered products.
                            Gdx.app.log("ORDER_STATE", "FAILED/OWNED");
                            break;
                        case OrderStatusCode.ORDER_STATE_SUCCESS:
                            // pay success.
                            String inAppPurchaseData = purchaseResultInfo.getInAppPurchaseData();

                            try {
                                InAppPurchaseData inAppPurchaseDataItem = new InAppPurchaseData(inAppPurchaseData);
                                Transaction transaction = new Transaction();
                                transaction.setIdentifier(inAppPurchaseDataItem.getProductId());
                                Gdx.app.log("ORDER_STATE", "SUCCESS productId: " + transaction.getIdentifier());

                                //TODO: REMOVE THE FOLLOWING LINES, ARE ONLY TO FORCE THE CONSUMABLE TEST
                                if (inAppPurchaseDataItem.getPurchaseType() == IapClient.PriceType.IN_APP_CONSUMABLE) {
                                    ((HuaweiPurchaseManager) game.purchaseManager).consumeProduct(inAppPurchaseData);
                                }
                            } catch (JSONException e) {
                                Gdx.app.error("ORDER_STATE_SUCCESS", e.getMessage());
                            }
                            break;
                    }
                }
                break;
        }
    }
}
