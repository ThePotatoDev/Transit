package gg.tater.transit.datastore;

import gg.tater.transit.model.TransitPlayerDataInfo;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.terminable.module.TerminableModule;

import java.util.UUID;

public interface TransitDatastoreDao extends TerminableModule {

    Promise<TransitPlayerDataInfo> getPlayerDataInfo(UUID uuid);

    TransitDatastoreType getType();

}
