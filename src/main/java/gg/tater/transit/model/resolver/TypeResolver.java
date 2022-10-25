package gg.tater.transit.model.resolver;

public abstract class TypeResolver<T> {

    public abstract String mask(Object object);

    public abstract T resolve(String input);

}
