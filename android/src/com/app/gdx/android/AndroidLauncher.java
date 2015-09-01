package com.app.gdx.android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import mi.maya.gdx.MainMenu;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //スリープしない
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new MainMenu() {

            @Override
            public void startButtonAction() {
                Log.d("INFO", "ログインボタンが押されました");
                startActivity(new Intent(AndroidLauncher.this, AndroidGameScreen.class));
            }

            @Override
            public void loginButtonAction() {
                Log.d("INFO", "ログインボタンが押されました");
                startActivity(new Intent(AndroidLauncher.this, AndroidTwitterOAuth.class));
            }

        }, config);
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
