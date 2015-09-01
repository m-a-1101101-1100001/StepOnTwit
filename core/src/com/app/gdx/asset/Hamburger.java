package com.app.gdx.asset;

import com.app.gdx.GameValue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hamburger extends Actor{
    World world;
    Sprite sprite;
    Body body;
    float visualMeter;

    public final float size;
    public Vector2 position;



    public Hamburger(World world, float size, Vector2 position) {
        this.world = world;
        this.size = size;
        this.position = position;

        create();
    }

    public void create() {
        //float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        visualMeter = new GameValue().visualMeter;

        Texture texture = new Texture("image/hamburger.png");
        sprite = new Sprite(texture);
        sprite.setSize(size, size);
        sprite.setOriginCenter();
        sprite.setPosition(position.x, position.y);
        /*sprite.setSize(
                w / 8f, w / 8f);
        sprite.setOriginCenter();
        sprite.setPosition(
                (w / 2f + sprite.getWidth()) / 2f, (h / 2f));*/

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (sprite.getX() + sprite.getWidth() / 2f) / visualMeter,
                (sprite.getY() + sprite.getHeight() / 2f) / visualMeter);
        bodyDef.angle = 0.05f;

        body = world.createBody(bodyDef);

        MassData massData = body.getMassData();
        massData.mass = 0.3f;

        body.setMassData(massData);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(
                sprite.getWidth() / 2f / visualMeter, sprite.getHeight() / 2f / visualMeter);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        polygonShape.dispose();
    }

    private void update() {
        sprite.setPosition(
                (body.getPosition().x * visualMeter) - sprite.getWidth() / 2f,
                (body.getPosition().y * visualMeter) - sprite.getHeight() / 2f);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

        position.x = sprite.getX();
        position.y = sprite.getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        update();

        batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
                sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(),
                sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
    }
}
