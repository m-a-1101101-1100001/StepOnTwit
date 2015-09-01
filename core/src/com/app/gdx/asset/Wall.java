package com.app.gdx.asset;

import com.app.gdx.GameValue;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by mayami on 2015/07/14.
 */
public class Wall{
    World world;
    Body[] bodies;
    float visualMeter;

    GameValue gameValue = new GameValue();

    public Wall(World world, float visualMeter) {
        this.world = world;
        this.visualMeter = visualMeter;
        create();
    }

    public void create() {
        float w = gameValue.visualScreenWidth / visualMeter;
        float h = gameValue.visualScreenheight / visualMeter;

        bodies = new Body[3];
        BodyDef[] bodyDef = new BodyDef[bodies.length];
        FixtureDef[] fixtureDef = new FixtureDef[bodies.length];
        EdgeShape[] edgeShape = new EdgeShape[bodies.length];

        for (int i = 0; i < bodies.length; i++) {
            bodyDef[i] = new BodyDef();
            bodyDef[i].type = BodyDef.BodyType.StaticBody;
            bodyDef[i].position.set(0f, 0f);

            fixtureDef[i] = new FixtureDef();
            edgeShape[i] = new EdgeShape();
        }

        edgeShape[0].set(-w / 2f, -h / 2f, w / 2f, -h / 2f);
        edgeShape[1].set(-w / 2f, -h / 2f, -w / 2f, h / 2f);
        edgeShape[2].set(w / 2f, -h / 2f, w / 2f, h / 2f);

        for (int i = 0; i < bodies.length; i++) {
            fixtureDef[i].shape = edgeShape[i];
            bodies[i] = world.createBody(bodyDef[i]);
            bodies[i].createFixture(fixtureDef[i]);

            edgeShape[i].dispose();
        }
    }
}
