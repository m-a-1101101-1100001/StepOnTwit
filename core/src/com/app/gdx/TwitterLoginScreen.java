package com.app.gdx;

import com.app.gdx.asset.TwitterBird;
import com.app.gdx.asset.Wall;
import com.app.gdx.asset.button.ButtonConfig;
import com.app.gdx.asset.textField.TextFieldConfig;
import com.app.gdx.twitter.TwitterAdapter;
import com.app.gdx.twitter.TwitterOAuth;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.badlogic.gdx.utils.viewport.FitViewport;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class TwitterLoginScreen extends ApplicationAdapter implements TwitterAdapter {
    Stage stage;
    World world;

    TwitterOAuth twitterOAuth;
    TwitterBird twitterBird;
    ImageButton loginButton;
    TextField textField;

    GameValue gameValue = new GameValue();


    @Override
    public void create() {
        float w = gameValue.visualScreenWidth;
        float h = gameValue.visualScreenheight;

        Vector2 textFieldSize = new Vector2(w / 4f, w / 4f / 3f);
        Vector2 textFieldPosition = new Vector2(-(textFieldSize.x / 2f), textFieldSize.y / 2f);
        Vector2 buttonSize = new Vector2(w / 4f, w / 4f / 4f);
        Vector2 logInButtonPosition = new Vector2(-(buttonSize.x / 2f), -(buttonSize.y * 1.2f));

        stage = new Stage(new FitViewport(w, h));
        stage.getViewport().setCamera(new OrthographicCamera(w, h));
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        Gdx.input.setInputProcessor(stage);

        world = new World(new Vector2(0f, -9.8f), true);


        twitterBird = new TwitterBird(world, gameValue.visualMeter);
        new Wall(world, gameValue.visualMeter);

        //TODO
        textField = new TextFieldConfig(textFieldSize, textFieldPosition, "PIN", new TextField.TextFieldStyle(
                new BitmapFont(Gdx.files.getFileHandle("font/font.fnt", Files.FileType.Internal), false),
                Color.BLACK, null, null, new SpriteDrawable(new Sprite(new Texture("image/textField.png"))))).create();
        textField.setVisible(false);

        loginButton = new ButtonConfig(buttonSize, logInButtonPosition, "image/loginButton.png").create();
        loginButton.addListener(new ClickListener() {
            boolean fulg = false;

            @Override
            public boolean touchDown(
                    InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("INFO", "ログインボタンが押されました");

                AsyncTask task = new AsyncTask() {
                    @Override
                    public Object call() throws Exception {
                        if (!fulg) {
                            fulg = true;//ボタンの動作がかわる
                            textField.setVisible(fulg);//テキストフィールドの表示

                            twitterOAuth = new TwitterOAuth();
                            openBrowser();//webブラウザが開かれる
                        } else {
                            twitterOAuth.Authorization(textField.getText());
                            saveTwitter(twitterOAuth.getAccessToken());
                            //TODO ログイン完了処理を実装する
                        }
                        return null;
                    }
                };

                try {
                    task.call();
                } catch (TwitterException e) {
                    Gdx.app.error("ERROR", e.getMessage());
                } catch (Exception e) {
                    Gdx.app.error("ERROR", e.getMessage());
                }

                return true;
            }
        });

        loginButton.setPosition(
                -loginButton.getWidth() / 2f,
                -(h / 2f) + (loginButton.getHeight() / 0.5f));

        stage.getViewport().setCamera(new OrthographicCamera(w, h));
        //stageにscene2dで作成したアセットを追加する
        stage.addActor(twitterBird);
        stage.addActor(textField);
        stage.addActor(loginButton);
    }


    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(239f / 255f, 161f / 255f, 143f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(deltaTime, 6, 2);

        stage.act(deltaTime);
        stage.draw();
    }


    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
    }


    public String getUrl() {
        return twitterOAuth.authorizationUrl;
    }


    public void openBrowser() {
        //webブラウザを開く処理がAndroidTwitterOAuthで実装される
    }


    @Override
    public AccessToken loadTwitter() {
        //保存したアクセストークンの読み込みがAndroidTwitterOAuthで実装される
        return null;
    }


    @Override
    public void saveTwitter(AccessToken accessToken) {
        //アクセストークンの保存がAndroidTwitterOAuthで実装される
    }
}

