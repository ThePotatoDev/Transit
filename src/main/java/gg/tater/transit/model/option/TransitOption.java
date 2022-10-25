package gg.tater.transit.model.option;

import gg.tater.transit.datastore.TransitDatastoreDao;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class TransitOption implements TerminableModule {

    private final TransitDatastoreDao dao;
    private final FileConfiguration config;
    private final Logger logger;
    private final String friendly;

    private boolean enabled;

}