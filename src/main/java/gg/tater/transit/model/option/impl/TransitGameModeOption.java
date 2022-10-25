package gg.tater.transit.model.option.impl;

import gg.tater.transit.TransitService;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.model.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import javax.annotation.Nonnull;

public class TransitGameModeOption extends TransitOption {

    public TransitGameModeOption() {
        super("TransitGameModeOption");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Helper.service(TransitService.class).ifPresent(service -> {
            Events.subscribe(TransitCompleteDataLoadEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();
                        TransitPlayerDataInfo info = event.getData();

                        info.getMetaValue(TransitPlayerDataInfo.META_GAME_MODE_KEY, GameMode.class)
                                .ifPresent(mode -> {
                                    player.setGameMode(mode);
                                    service.getLogger().info("Set " + player.getName() + "'s game mode to " + mode.name() + ".");
                                });
                    }).bindWith(consumer);

            Events.subscribe(PlayerGameModeChangeEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();
                        service.getDao()
                                .getPlayerDataInfo(player.getUniqueId())
                                .thenAcceptAsync(info -> info.setMetaValue(TransitPlayerDataInfo.META_GAME_MODE_KEY, event.getNewGameMode()));
                    }).bindWith(consumer);
        });
    }
}
