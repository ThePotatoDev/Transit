package gg.tater.transit.option.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.option.TransitOption;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

public class TransitEnderChestOption extends TransitOption {

    public TransitEnderChestOption(TransitDatastoreDao dao, FileConfiguration config, Logger logger) {
        super(dao, config, logger, "Ender Chest");
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {

    }
}
