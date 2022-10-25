package gg.tater.transit.model.option.impl;

import com.google.gson.JsonPrimitive;
import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.model.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.serialize.Serializers;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class TransitEnderChestOption extends TransitOption {

    public TransitEnderChestOption(TransitDatastoreDao dao, FileConfiguration config, Logger logger) {
        super(dao, config, logger, "Ender Chest");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Events.subscribe(TransitCompleteDataLoadEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    TransitPlayerDataInfo info = event.getData();

                    info.getMetaValue(TransitPlayerDataInfo.META_ENDER_CHEST_KEY, JsonPrimitive.class)
                            .ifPresent(primitive -> {
                                player.getEnderChest().setContents(Serializers.deserializeInventory(primitive, "Ender Chest").getContents());
                                getLogger().info("Deserialized ender chest inventory contents for: " + player.getName());
                            });
                }).bindWith(consumer);

        Events.subscribe(PlayerQuitEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    Inventory inventory = player.getEnderChest();

                    getDao().getPlayerDataInfo(player.getUniqueId())
                            .thenAcceptAsync(info -> {
                                info.setMetaValue(TransitPlayerDataInfo.META_ENDER_CHEST_KEY, Serializers.serializeInventory(inventory));
                                getLogger().info("Stored ender chest inventory contents for: " + player.getName());
                            });
                }).bindWith(consumer);
    }
}
