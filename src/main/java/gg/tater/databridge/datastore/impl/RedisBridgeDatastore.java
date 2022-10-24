package gg.tater.databridge.datastore.impl;

import gg.tater.databridge.datastore.BridgeDatastoreDao;
import gg.tater.databridge.datastore.BridgeDatastoreType;
import me.lucko.helper.Helper;
import me.lucko.helper.redis.Redis;
import me.lucko.helper.terminable.TerminableConsumer;

import javax.annotation.Nonnull;

public class RedisBridgeDatastore implements BridgeDatastoreDao {

    private Redis redis;

    @Override
    public BridgeDatastoreType getType() {
        return BridgeDatastoreType.MONGO;
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        this.redis = Helper.serviceNullable(Redis.class);
    }
}
