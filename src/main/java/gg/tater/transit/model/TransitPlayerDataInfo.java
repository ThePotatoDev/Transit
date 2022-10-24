package gg.tater.transit.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.lucko.helper.gson.GsonSerializable;
import me.lucko.helper.gson.JsonBuilder;
import org.bukkit.GameMode;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
@Getter
@Setter
public class TransitPlayerDataInfo implements GsonSerializable {

    private static final String GAME_MODE_FIELD = "player_gamemode";

    private GameMode gameMode = GameMode.SURVIVAL;

    @Nonnull
    @Override
    public JsonElement serialize() {
        return JsonBuilder
                .object()
                .add(GAME_MODE_FIELD, gameMode.name())
                .build();
    }

    public static TransitPlayerDataInfo deserialize(JsonElement element) {
        JsonObject object = (JsonObject) element;

        TransitPlayerDataInfo info = new TransitPlayerDataInfo();
        info.setGameMode(GameMode.valueOf(object.get(GAME_MODE_FIELD).getAsString()));

        return info;
    }
}
