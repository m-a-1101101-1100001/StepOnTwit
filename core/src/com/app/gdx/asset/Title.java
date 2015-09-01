package com.app.gdx.asset;

import com.app.gdx.GameValue;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


/**
 * Created by mayami on 2015/07/15.
 */
public class Title{
    World world;
    Sprite[] sprite = new Sprite[10];
    Body[] bodies = new Body[sprite.length];
    BitmapFont bitmapFont;

    GameValue gameValue = new GameValue();

    public Title(World world) {
        this.world = world;
        create();
    }

    public void create() {
        final float w = gameValue.visualScreenWidth;
        final float h = gameValue.visualScreenheight;

        bitmapFont = new BitmapFont(Gdx.files.getFileHandle("font/font.fnt", Files.FileType.Internal), true);
        Array<TextureRegion> array = new Array<TextureRegion>(sprite.length);
        /*font.fntの座標をもとにしています*/
        array.add(new TextureRegion(bitmapFont.getRegion(), 0, 248, 54, 46));  //S
        array.add(new TextureRegion(bitmapFont.getRegion(), 450, 202, 54, 46));//T
        array.add(new TextureRegion(bitmapFont.getRegion(), 199, 294, 54, 46));//E
        array.add(new TextureRegion(bitmapFont.getRegion(), 108, 248, 54, 46));//P
        array.add(new TextureRegion(bitmapFont.getRegion(), 162, 248, 54, 46));//O
        array.add(new TextureRegion(bitmapFont.getRegion(), 216, 248, 54, 46));//N
        array.add(new TextureRegion(bitmapFont.getRegion(), 450, 202, 54, 46));//T
        array.add(new TextureRegion(bitmapFont.getRegion(), 270, 202, 72, 46));//W
        array.add(new TextureRegion(bitmapFont.getRegion(), 0, 294, 37, 46));  //I
        array.add(new TextureRegion(bitmapFont.getRegion(), 450, 202, 54, 46));//T

        float x = 0f;
        for (int i = 0; i < array.size; i++) {
            sprite[i] = new Sprite(array.get(i));
            sprite[i].setSize(
                    w / 15f, w / 15f);
            sprite[i].setOriginCenter();
            sprite[i].setColor(1, 1, 1, 1);
            x += sprite[i].getWidth();
            sprite[i].setPosition(
                    -w / 2f + sprite[i].getWidth() + x, h / 2f - sprite[i].getHeight());

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(
                    (sprite[i].getX() + sprite[i].getWidth() / 2f) / gameValue.visualMeter,
                    (sprite[i].getY() + sprite[i].getHeight() / 2f) / gameValue.visualMeter);
            bodyDef.angle = (i % 2 == 0) ? 0.4f : -0.4f;

            bodies[i] = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(
                    sprite[i].getWidth() / 2f / gameValue.visualMeter,
                    sprite[i].getHeight() / 2f / gameValue.visualMeter);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape;
            fixtureDef.density = 0.1f;
            fixtureDef.restitution = 0.3f;

            bodies[i].createFixture(fixtureDef);
            polygonShape.dispose();
        }
    }

    public void update() {
        for (int i = 0; i < sprite.length; i++) {
            sprite[i].setPosition(
                    (bodies[i].getPosition().x * gameValue.visualMeter) - sprite[i].getWidth() / 2f,
                    (bodies[i].getPosition().y * gameValue.visualMeter) - sprite[i].getHeight() / 2f);
            sprite[i].setRotation((float) Math.toDegrees(bodies[i].getAngle()));
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < sprite.length; i++)
            sprite[i].draw(batch);
    }

    public void dispose() {
        bitmapFont.dispose();
        for (Sprite s : sprite)
            s.getTexture().dispose();
    }
}
