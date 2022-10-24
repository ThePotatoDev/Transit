package gg.tater.transit.datastore.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.datastore.TransitDatastoreType;
import gg.tater.transit.model.TransitPlayerDataInfo;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.terminable.TerminableConsumer;

import javax.annotation.Nonnull;
import java.util.UUID;

public class MongoTransitDatastore implements TransitDatastoreDao {

    private static final String COLLECTION_NAME = "bridge-data-info";

    @Override
    public Promise<TransitPlayerDataInfo> getPlayerDataInfo(UUID uuid) {
        return null;
    }

    @Override
    public TransitDatastoreType getType() {
        return TransitDatastoreType.MONGO;
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {

    }
}
