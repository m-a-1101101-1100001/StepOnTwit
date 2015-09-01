package com.app.gdx.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * 境界線を作るためのクラス
 * 線を描画しなくてもよい場合はdrawしない
 * //TODO wallクラスからBoundに移行する
 */
public class Bound{
    ShapeRenderer shapeRenderer;
    Color color;
    Vector2 start;
    Vector2 end;

    World world;
    Body body;
    float visualMeter;


    public Bound(ShapeRenderer shapeRenderer, Color color, Vector2 start, Vector2 end, World world, float visualMeter) {
        this.shapeRenderer = shapeRenderer;
        this.color = color;
        this.start = start;
        this.end = end;
        this.world = world;
        this.visualMeter = visualMeter;

        create();
    }


    void create() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(
                (start.x / visualMeter), (start.y / visualMeter),
                (end.x / visualMeter), (end.y / visualMeter));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;

        body.createFixture(fixtureDef);
        edgeShape.dispose();
    }


    public void draw(Batch batch) {
        batch.begin();

        Gdx.gl.glLineWidth(2f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.line(start, end);
        shapeRenderer.end();

        batch.end();
    }
}


