package com.app.gdx.android.twitter;

import android.app.Activity;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import twitter4j.auth.AccessToken;

/**
 * Created by mayami on 2015/07/22.
 * twitterのaccessTokenを保存および読み込みを行うクラス
 */
public class AndroidAccessTokenData {
    Activity activity;
    private String DIRECTORY;
    private String FILE = "twitter4j.mi";

    public AndroidAccessTokenData(Activity activity){
        this.activity = activity;
        DIRECTORY = activity.getFilesDir().getAbsolutePath();
    }
    /**
     * 保存先の指定をする
     */
    private File getFile(){
        File dir = new File(DIRECTORY);
        if (dir.mkdirs())
            Gdx.app.log("INFO", "ディレクトリを作成します");
        Gdx.app.log("INFO", dir.getAbsolutePath());

        return new File(dir, FILE);
    }

    public AccessToken load() {
        AccessToken accessToken = null;

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getFile()));
            accessToken = (AccessToken) inputStream.readObject();
            inputStream.close();
        } catch (ClassNotFoundException e) {
            Gdx.app.error("ERROR", e.getMessage());
            return null;
        } catch (IOException e) {
            Gdx.app.error("ERROR", e.getMessage());
            return null;
        }

        Gdx.app.log("INFO", "load :" + accessToken.getToken());
        return accessToken;
    }

    /**
     * アクセストークンを保存する
     * @param accessToken TwitterOAuth.classで取得したものをセットする
     */
    public void save(AccessToken accessToken) {
        Gdx.app.log("INFO", "アクセストークンを保存します");

        ObjectOutputStream outputStream;

        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(getFile()));
            outputStream.writeObject(accessToken);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Gdx.app.error("ERROR", e.getMessage());
        } catch (IOException e) {
            Gdx.app.error("ERROR", e.getMessage());
        }
        Gdx.app.log("INFO", "saved :" + load().getToken().toString());
    }
}
