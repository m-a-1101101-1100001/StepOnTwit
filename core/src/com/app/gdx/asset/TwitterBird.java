package com.app.gdx.asset;

import com.app.gdx.GameValue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TwitterBird extends Actor {
    Sprite[] sprite = new Sprite[15];
    World world;
    Body[] body = new Body[sprite.length];
    float visualMeter;

    GameValue gameValue = new GameValue();


    public TwitterBird(World world, float visualMeter) {
        this.world = world;
        this.visualMeter = visualMeter;
        create();
    }


    public void create() {
        float w = gameValue.visualScreenWidth;
        float h = gameValue.visualScreenheight;

        for (int i = 0; i < sprite.length; i++) {
            sprite[i] = new Sprite(new Texture("image/twitterLogo.png"));
            sprite[i].setSize(w / 20f, w / 20f);
            sprite[i].setOriginCenter();
            float psw = (float) (Math.random() * w) / 2f;
            sprite[i].setPosition((i % 2 == 0) ? psw : -psw, (float) (Math.random() * h));

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(
                    (sprite[i].getX() + sprite[i].getWidth() / 2f) / visualMeter,
                    (sprite[i].getY() + sprite[i].getHeight() / 2f) / visualMeter);
            bodyDef.angle = 0.05f;

            body[i] = world.createBody(bodyDef);

            MassData massData = new MassData();
            massData.mass = (i % 2 == 0) ? 0.5f : -0.5f;

            body[i].setMassData(massData);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(
                    sprite[i].getWidth() / 2f / visualMeter, sprite[i].getHeight() / 2f / visualMeter);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 0.5f;
            fixtureDef.restitution = 0.5f;

            body[i].createFixture(fixtureDef);
            //     body[i].setFixedRotation(true);
            polygonShape.dispose();
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (int i = 0; i < sprite.length; i++) {
            sprite[i].setPosition(
                    (body[i].getPosition().x * visualMeter) - (sprite[i].getWidth() / 2f),
                    (body[i].getPosition().y * visualMeter) - (sprite[i].getHeight() / 2f));
            sprite[i].setRotation((float) Math.toDegrees(body[i].getAngle()));
            sprite[i].draw(batch);
        }
    }
}
