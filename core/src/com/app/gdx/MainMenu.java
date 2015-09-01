package com.app.gdx;

import com.app.gdx.asset.Hamburger;
import com.app.gdx.asset.Title;
import com.app.gdx.asset.Wall;
import com.app.gdx.asset.button.ButtonConfig;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainMenu extends ApplicationAdapter {
    Stage stage;
    World world;

    Title title;
    Hamburger hamburger;
    ImageButton startButton;
    ImageButton loginButton;

    GameValue gameValue = new GameValue();

    @Override
    public void create() {
        final float w = gameValue.visualScreenWidth;
        final float h = gameValue.visualScreenheight;

        stage = new Stage(new FitViewport(w, h));
        stage.getViewport().setCamera(new OrthographicCamera(w, h));
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        Gdx.input.setInputProcessor(stage);

        world = new World(new Vector2(0f, -9.8f), true);
        //アセットのロード
        title = new Title(world);

        float humburgerSize = w / 8f;
        hamburger = new Hamburger(world, humburgerSize,
                new Vector2((w / 2f + humburgerSize) / 2f, (h / 2f)));

        new Wall(world, gameValue.visualMeter);//TODO


        Vector2 buttonSize = new Vector2(w / 4f, w / 4f / 4f);
        Vector2 startButtonPosition = new Vector2(-(buttonSize.x / 2f), 0f);
        Vector2 logInButtonPosition = new Vector2(-(buttonSize.x / 2f), -(buttonSize.y * 1.2f));

        startButton = new ButtonConfig(buttonSize, startButtonPosition, "image/startButton.png").create();
        loginButton = new ButtonConfig(buttonSize, logInButtonPosition, "image/loginButton.png").create();

        startButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                startButtonAction();
                return true;
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                loginButtonAction();//アクティビティの推移
                return true;
            }
        });

        //stageにscene2dで作成したアセットを追加する
        stage.addActor(hamburger);
        stage.addActor(startButton);
        stage.addActor(loginButton);
    }


    private void update() {
        //hamburger.update();
        //stage.getCamera().update();
        title.update();
    }


    @Override
    public void render() {
        update();
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.step(deltaTime, 6, 2);

        Gdx.gl.glClearColor(239f / 255f, 161f / 255f, 143f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.getBatch().setProjectionMatrix(stage.getCamera().combined);

        stage.act(deltaTime);
        stage.draw();

        stage.getBatch().begin();
        title.draw((SpriteBatch) stage.getBatch());
        stage.getBatch().end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        title.dispose();
    }


    public void startButtonAction() {
        //AndroidLauncherでstartButtonが押された際のアクティビティ推移を実装する
    }


    public void loginButtonAction() {
        //AndroidLauncherでloginButtonが押された際のアクティビティ推移を実装する
    }
}
