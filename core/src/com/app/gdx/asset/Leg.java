package com.app.gdx.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Leg extends Actor {
    Sprite sprite;
    World world;
    Body body;
    float visualMeter;

    String file;
    float x, y;

    public Leg(String file, World world, float visualMeter, float x, float y){
        this.file = file;
        this.world = world;
        this.visualMeter = visualMeter;
        this.x = x; this.y = y;

        create();
    }


    void create(){
        float h = Gdx.graphics.getHeight();

        sprite = new Sprite(new Texture(file));
        sprite.setSize(h * 1.2f / 4f, h * 1.2f);
        sprite.setOriginCenter();
        sprite.setPosition(x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;//オブジェクトが倒れるのを禁止する
        bodyDef.position.set(
                (sprite.getX() + sprite.getWidth() / 2f) / visualMeter,
                (sprite.getY() + sprite.getHeight() / 2f) / visualMeter);

        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(
                sprite.getWidth() / 2f / visualMeter, sprite.getHeight() / 2f / visualMeter);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }


    public void draw(Batch batch, float parentAlpha) {
        Gdx.app.log("Value", String.valueOf(sprite.getX() + ":" + sprite.getY()));
        //body.applyForce(new Vector2(0f, 1500f), body.getPosition(), true);//TODO テスト
        sprite.setPosition(
                body.getPosition().x * visualMeter - sprite.getWidth() / 2f,
                body.getPosition().y * visualMeter - sprite.getHeight() / 2f);

        sprite.draw(batch);
    }
}
