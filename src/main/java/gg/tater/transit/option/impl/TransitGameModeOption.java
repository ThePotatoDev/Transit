package gg.tater.transit.option.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.event.custom.TransitCompleteLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class TransitGameModeOption extends TransitOption {

    private static final String LOGGER_PREFIX = "[TransitGameModeOption] ";

    public TransitGameModeOption(TransitDatastoreDao dao, FileConfiguration config, Logger logger) {
        super(dao, config, logger, "TransitGameModeOption");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        if (!getConfig().getBoolean("game-mode-option")) {
            getLogger().info("GameMode transit option disabled.");
            setEnabled(false);
            return;
        }

        setEnabled(true);
        getLogger().info(LOGGER_PREFIX + "Enabled GameMode transit option.");

        Events.subscribe(TransitCompleteLoadEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    TransitPlayerDataInfo info = event.getData();

                    info.getMetaValue(TransitPlayerDataInfo.META_GAME_MODE_KEY, GameMode.class)
                            .ifPresent(mode -> {
                                player.setGameMode(mode);
                                getLogger().info(LOGGER_PREFIX + "Set " + player.getName() + "'s game mode to " + mode.name() + ".");
                            });
                }).bindWith(consumer);

        Events.subscribe(PlayerGameModeChangeEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    getDao().getPlayerDataInfo(player.getUniqueId())
                            .thenAcceptAsync(info -> info.setMetaValue(TransitPlayerDataInfo.META_GAME_MODE_KEY, event.getNewGameMode()));
                }).bindWith(consumer);
    }
}
