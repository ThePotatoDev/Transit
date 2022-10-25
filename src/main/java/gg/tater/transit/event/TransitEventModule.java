package gg.tater.transit.event;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.event.custom.TransitCompleteDataLoadEvent;
import lombok.RequiredArgsConstructor;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class TransitEventModule implements TerminableModule {

    private final Logger logger;
    private final TransitDatastoreDao dao;

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Events.subscribe(PlayerLoginEvent.class)
                .handler(event -> {
                    Player player = event.getPlayer();
                    dao.getPlayerDataInfo(player.getUniqueId()).thenAcceptSync(info -> Events.call(new TransitCompleteDataLoadEvent(player, info)));
                })
                .bindWith(consumer);
    }
}
