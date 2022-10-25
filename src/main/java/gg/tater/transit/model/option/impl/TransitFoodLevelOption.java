package gg.tater.transit.model.option.impl;

import gg.tater.transit.TransitService;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.model.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;

public class TransitFoodLevelOption extends TransitOption {

    public TransitFoodLevelOption() {
        super("Food Level");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Helper.service(TransitService.class).ifPresent(service -> {

            Events.subscribe(TransitCompleteDataLoadEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();
                        TransitPlayerDataInfo info = event.getData();

                        info.getMetaValue(TransitPlayerDataInfo.META_FOOD_LEVEL_KEY, Integer.class)
                                .ifPresent(level -> {
                                    player.setFoodLevel(level);
                                    service.getLogger().info("Set player food level to: " + level);
                                });
                    }).bindWith(consumer);

            Events.subscribe(PlayerQuitEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();

                    }).bindWith(consumer);
        });
    }
}
