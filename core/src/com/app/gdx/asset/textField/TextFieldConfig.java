package com.app.gdx.asset.textField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class TextFieldConfig {
    private TextField textField;


    public TextFieldConfig(Vector2 size, Vector2 position, String defaultString, TextField.TextFieldStyle textFieldStyle){
        textField = new TextField(defaultString, textFieldStyle);
        textField.setSize(size.x, size.y);
        textField.setOrigin(size.x / 2f, size.y / 2f);
        textField.setPosition(position.x, position.y);
    }

    public TextField create(){
        return textField;
    }
}
