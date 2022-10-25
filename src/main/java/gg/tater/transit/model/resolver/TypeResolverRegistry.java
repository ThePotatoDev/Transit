package gg.tater.transit.model.resolver;

import com.google.gson.JsonPrimitive;
import gg.tater.transit.model.resolver.impl.GameModeTypeResolver;
import gg.tater.transit.model.resolver.impl.JsonPrimitiveTypeResolver;
import org.bukkit.GameMode;

import java.util.IdentityHashMap;
import java.util.Map;

public class TypeResolverRegistry {

    private static final Map<Class<?>, TypeResolver<?>> CLASS_TYPE_RESOLVER_MAP = new IdentityHashMap<>();

    static {
        CLASS_TYPE_RESOLVER_MAP.put(GameMode.class, new GameModeTypeResolver());
        CLASS_TYPE_RESOLVER_MAP.put(JsonPrimitive.class, new JsonPrimitiveTypeResolver());
    }

    public static TypeResolver<?> getTypeResolver(Class<?> clazz) {
        return CLASS_TYPE_RESOLVER_MAP.get(clazz);
    }
}
