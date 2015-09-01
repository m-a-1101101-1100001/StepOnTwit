package com.app.gdx.android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.app.gdx.android.twitter.AndroidAccessTokenData;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import mi.maya.gdx.TwitterLoginScreen;
import twitter4j.auth.AccessToken;

public class AndroidTwitterOAuth extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);//ナビゲーションバーが一瞬標準されてしまうので強制的に呼び出す
        //スリープしない
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new TwitterLoginScreen() {
            AndroidAccessTokenData tokenData =
                    new AndroidAccessTokenData(AndroidTwitterOAuth.this);

            @Override
            public void openBrowser() {
                Uri uri = Uri.parse(getUrl());
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }

            @Override
            public void saveTwitter(AccessToken accessToken) {
                tokenData.save(accessToken);
            }

            @Override
            public AccessToken loadTwitter() {
                return tokenData.load();
            }
        }, config);
        //initialize(new Twitter4jTest(), config);
    }

    //フルスクリーンに設定する
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View view = getWindow().getDecorView();
        if (hasFocus)
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
