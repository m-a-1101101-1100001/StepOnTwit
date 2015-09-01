package com.app.gdx.utility;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


public class JsonGenerator {
    private Json json;
    private FileHandle handle;


    public JsonGenerator(FileHandle handle) {
        this.handle = handle;
        json = new Json();
    }


    public void writeJson(Object target) {
        String code = json.prettyPrint(target);
        handle.writeString(code, false);
    }



    public Object readJson(Class type) {
        Object object = json.fromJson(type, handle);
        return object;
    }


    public void readJson(Class type, String tag, Class elementType) {
        json.setElementType(type, tag, elementType);
    }
}
