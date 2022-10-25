package gg.tater.transit.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.tater.transit.model.resolver.TypeResolver;
import gg.tater.transit.model.resolver.TypeResolverRegistry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.lucko.helper.gson.GsonSerializable;
import me.lucko.helper.gson.JsonBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Setter
public class TransitPlayerDataInfo implements GsonSerializable {

    public static final String META_FOOD_LEVEL_KEY = "food_level";
    public static final String META_GAME_MODE_KEY = "current_gamemode";
    public static final String META_ENDER_CHEST_KEY = "ender_chest";

    private static final String META_MAP_KEY = "meta_map";

    private final Map<String, String> metaMap = new HashMap<>();

    @Nonnull
    @Override
    public JsonElement serialize() {
        return JsonBuilder
                .object()
                .add(META_MAP_KEY, JsonBuilder.array()
                        .addAll(metaMap.entrySet()
                                .stream()
                                .map(entry -> JsonBuilder.object()
                                        .add(entry.getKey(), entry.getValue())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .build();
    }

    public static TransitPlayerDataInfo deserialize(JsonElement element) {
        JsonObject object = (JsonObject) element;

        TransitPlayerDataInfo info = new TransitPlayerDataInfo();

        object.get(META_MAP_KEY)
                .getAsJsonArray()
                .forEach(eachElement -> {
                    JsonObject eachObject = (JsonObject) eachElement;

                    eachObject.entrySet().forEach(entry -> {
                        String key = entry.getKey();
                        String value = entry.getValue().getAsString();
                        info.getMetaMap().put(key, value);
                    });

                });

        return info;
    }

    public <T> Optional<T> getMetaValue(String key, Class<T> clazz) {
        String storedValue = metaMap.get(key);

        TypeResolver<?> resolver = TypeResolverRegistry.getTypeResolver(clazz);
        Object resolved = resolver.resolve(storedValue);

        return Optional.ofNullable(clazz.cast(resolved));
    }

    public void setMetaValue(String key, Object object) {
        Class<?> clazz = object.getClass();
        TypeResolver<?> resolver = TypeResolverRegistry.getTypeResolver(clazz);
        metaMap.put(key, resolver.mask(object));
    }
}
