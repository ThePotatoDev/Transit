package gg.tater.transit.model.resolver.impl;

import com.google.gson.JsonPrimitive;
import gg.tater.transit.model.resolver.TypeResolver;

public class JsonPrimitiveTypeResolver extends TypeResolver<JsonPrimitive> {

    @Override
    public String mask(Object object) {
        JsonPrimitive primitive = (JsonPrimitive) object;
        return primitive.getAsString();
    }

    @Override
    public JsonPrimitive resolve(String input) {
        return new JsonPrimitive(input);
    }
}
