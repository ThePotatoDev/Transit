package gg.tater.transit.model.resolver;

import gg.tater.transit.model.resolver.impl.GameModeTypeResolver;
import org.bukkit.GameMode;

import java.util.IdentityHashMap;
import java.util.Map;

public class TypeResolverRegistry {

    private static final Map<Class<?>, TypeResolver<?>> CLASS_TYPE_RESOLVER_MAP = new IdentityHashMap<>();

    static {
        CLASS_TYPE_RESOLVER_MAP.put(GameMode.class, new GameModeTypeResolver());
    }

    public static TypeResolver<?> getTypeResolver(Class<?> clazz) {
        return CLASS_TYPE_RESOLVER_MAP.get(clazz);
    }
}
