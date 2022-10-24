package gg.tater.transit.option.impl;

import gg.tater.transit.event.custom.TransitCompleteLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class TransitGameModeOption extends TransitOption {

    private static final String LOGGER_PREFIX = "[TransitGameModeOption] ";

    public TransitGameModeOption(FileConfiguration config, Logger logger) {
        super(config, logger, "TransitGameModeOption");
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

                    player.setGameMode(info.getGameMode());
                    getLogger().info(LOGGER_PREFIX + "Set " + player.getName() + "'s game mode to " + info.getGameMode().name() + ".");
                }).bindWith(consumer);
    }
}
