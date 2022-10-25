package gg.tater.transit.model.option.impl;

import com.google.gson.JsonPrimitive;
import gg.tater.transit.TransitService;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import gg.tater.transit.model.TransitPlayerDataInfo;
import gg.tater.transit.model.option.TransitOption;
import me.lucko.helper.Events;
import me.lucko.helper.Helper;
import me.lucko.helper.serialize.Serializers;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;

public class TransitEnderChestOption extends TransitOption {

    public TransitEnderChestOption() {
        super("Ender Chest");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Helper.service(TransitService.class).ifPresent(service -> {
            Events.subscribe(TransitCompleteDataLoadEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();
                        TransitPlayerDataInfo info = event.getData();

                        info.getMetaValue(TransitPlayerDataInfo.META_ENDER_CHEST_KEY, JsonPrimitive.class)
                                .ifPresent(primitive -> {
                                    player.getEnderChest().setContents(Serializers.deserializeInventory(primitive, "Ender Chest").getContents());
                                    service.getLogger().info("Deserialized ender chest inventory contents for: " + player.getName());
                                });
                    }).bindWith(consumer);

            Events.subscribe(PlayerQuitEvent.class)
                    .handler(event -> {
                        Player player = event.getPlayer();
                        Inventory inventory = player.getEnderChest();

                        service.getDao().getPlayerDataInfo(player.getUniqueId())
                                .thenAcceptAsync(info -> {
                                    info.setMetaValue(TransitPlayerDataInfo.META_ENDER_CHEST_KEY, Serializers.serializeInventory(inventory));
                                    service.getLogger().info("Stored ender chest inventory contents for: " + player.getName());
                                });
                    }).bindWith(consumer);
        });
    }
}
