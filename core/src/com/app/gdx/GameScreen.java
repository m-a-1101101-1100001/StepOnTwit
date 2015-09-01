package com.app.gdx;

import com.app.gdx.asset.Bound;
import com.app.gdx.asset.Leg;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends ApplicationAdapter {
    Stage stage;
    SpriteBatch spriteBatch;
    Camera camera;
    ShapeRenderer renderer;

    World world;
    float visualMeter = 100f;

    Leg rightLeg;
    Bound ground;


    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        /*stage.getViewport().setCamera(new PerspectiveCamera(67f, w, h));
        stage.getCamera().position.set(2,2,2);
        stage.getCamera().lookAt(0,0,0);
        stage.getCamera().far = 300f;
        stage.getCamera().update();*/

        stage = new Stage();
        stage = new Stage(new FitViewport(w, h));
        stage.getViewport().setCamera(new OrthographicCamera(w, h));
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        Gdx.input.setInputProcessor(stage);

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(stage.getCamera().combined);

        world = new World(new Vector2(0f, -9.8f), true);

        ground = new Bound(renderer, Color.WHITE, new Vector2(-w / 2f, -h / 1.1f / 2f), new Vector2(w / 2f, -h / 1.1f/ 2), world, visualMeter);

        rightLeg = new Leg("image/rightLeg.png", world, visualMeter, -w / 2f, h / 2f);
        stage.addActor(rightLeg);


       /* world = new World(new Vector2(0f, -9.8f), true);
        //stage = new Stage(new FitViewport(w, h, new OrthographicCamera()));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();
        camera  = (OrthographicCamera) stage.getViewport().getCamera();
        //camera.setToOrtho(true, w / 2, h / 2);

        //ground = new Bound(stage, Color.WHITE, new Vector2(-w / 2f, (-h / 1.2f) / 2f), new Vector2(w / 2f, (-h / 1.2f) / 2f), world, visualMeter);
        //stage.addActor(ground);

        rightLeg = new Leg("image/rightLeg.png", world, visualMeter, -w / 2f, h / 2f);
        stage.addActor(rightLeg);

        if(!Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer))
            Gdx.app.log("Warning", "センサーはつかえません");*///TODO センサーが使えない場合の例外処理を実装する

    }


    void update() {
        //Gdx.app.log("Value", String.valueOf(Gdx.input.getAccelerometerY()));
    }


    @Override
    public void render() {
        //Gdx.app.log("Value", String.valueOf(rightLeg.getX() + ":" + rightLeg.getY()));

        float deltaTime = Gdx.graphics.getDeltaTime();
        world.step(deltaTime, 6, 2);

        Gdx.gl.glClearColor(239f / 255f, 161f / 255f, 143f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

        ground.draw(stage.getBatch());
    }


    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }


    @Override
    public void resume() {
    }
}
