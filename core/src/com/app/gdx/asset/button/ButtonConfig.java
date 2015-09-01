package com.app.gdx.asset.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


public class ButtonConfig{
    private ImageButton imageButton;


    public ButtonConfig(Vector2 size, Vector2 position, String backgroundImage){
        imageButton =
                new ImageButton(new SpriteDrawable(new Sprite(new Texture(backgroundImage))));
        imageButton.setSize(size.x, size.y);
        imageButton.setOrigin(size.x / 2f, size.y / 2f);
        imageButton.setPosition(position.x, position.y);
    }

    public ImageButton create(){
        return imageButton;
    }
}
