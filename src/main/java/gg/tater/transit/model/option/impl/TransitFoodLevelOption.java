package gg.tater.transit.model.option.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.model.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class TransitFoodLevelOption extends TransitOption {

    public TransitFoodLevelOption(TransitDatastoreDao dao, FileConfiguration config, Logger logger) {
        super(dao, config, logger, "Food Level");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Events.subscribe(TransitCompleteDataLoadEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    TransitPlayerDataInfo info = event.getData();

                    info.getMetaValue(TransitPlayerDataInfo.META_FOOD_LEVEL_KEY, Integer.class)
                            .ifPresent(level -> {
                                player.setFoodLevel(level);
                                getLogger().info("Set player food level to: " + level);
                            });
                }).bindWith(consumer);

        Events.subscribe(PlayerQuitEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();

                }).bindWith(consumer);
    }
}
