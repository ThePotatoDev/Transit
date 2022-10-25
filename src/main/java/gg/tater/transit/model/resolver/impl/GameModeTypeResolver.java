package gg.tater.transit.model.resolver.impl;

import gg.tater.transit.model.resolver.TypeResolver;
import org.bukkit.GameMode;

public class GameModeTypeResolver extends TypeResolver<GameMode> {

    @Override
    public String mask(Object object) {
        GameMode mode = (GameMode) object;
        return mode.name();
    }

    @Override
    public GameMode resolve(String input) {
        return GameMode.valueOf(input);
    }
}
