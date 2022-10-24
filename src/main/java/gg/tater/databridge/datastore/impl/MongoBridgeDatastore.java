package gg.tater.databridge.datastore.impl;

import gg.tater.databridge.datastore.BridgeDatastoreDao;
import gg.tater.databridge.datastore.BridgeDatastoreType;
import me.lucko.helper.terminable.TerminableConsumer;

import javax.annotation.Nonnull;

public class MongoBridgeDatastore implements BridgeDatastoreDao {

    @Override
    public BridgeDatastoreType getType() {
        return BridgeDatastoreType.MONGO;
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {

    }
}
