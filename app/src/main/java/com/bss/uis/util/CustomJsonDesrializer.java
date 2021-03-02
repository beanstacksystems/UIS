package com.bss.uis.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CustomJsonDesrializer<T> implements JsonDeserializer<T>
{
    private Class<T> mClass;
    private String mKey;
    public CustomJsonDesrializer(Class<T> targetClass,String jsonKeyName) {
        mClass = targetClass;
        mKey = jsonKeyName;
    }
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        // Get the "content" element from the parsed JSON
        JsonElement content = null;
        if(je.isJsonArray())
            content = je.getAsJsonArray().get(0).getAsJsonObject().get(mKey);
        else
            content = je.getAsJsonObject().get(mKey);
        if(content.isJsonArray())
            return new Gson().fromJson(content.getAsJsonArray(), mClass);

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, mClass);

    }

}
