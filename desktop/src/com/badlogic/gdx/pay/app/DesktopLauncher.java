package com.badlogic.gdx.pay.app;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.badlogic.gdx.pay.Information;
import com.badlogic.gdx.pay.PurchaseManager;
import com.badlogic.gdx.pay.PurchaseManagerConfig;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.Transaction;
import com.badlogic.gdx.pay.app.GdxPayApp;
import com.badlogic.gdx.utils.Array;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        GdxPayApp game = new GdxPayApp();
        game.purchaseManager = new MyTestPurchaseManager();
        new LwjglApplication(game);
    }

    private static class MyTestPurchaseManager implements PurchaseManager {
        PurchaseObserver observer;

        @Override
        public String storeName() {
            return "TEST";
        }

        @Override
        public void install(PurchaseObserver observer, PurchaseManagerConfig config, boolean autoFetchInformation) {
            this.observer = observer;
            observer.handleInstall();
        }

        @Override
        public boolean installed() {
            return true;
        }

        @Override
        public void dispose() {

        }

        @Override
        public void purchase(String identifier) {
            Transaction transaction = new Transaction();
            transaction.setIdentifier(identifier);
            observer.handlePurchase(transaction);
        }

        @Override
        public void purchaseRestore() {

        }

        @Override
        public Information getInformation(String identifier) {
            return null;
        }
    }
}
