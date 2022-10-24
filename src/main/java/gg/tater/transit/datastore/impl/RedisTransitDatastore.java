package gg.tater.transit.datastore.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.datastore.TransitDatastoreType;
import gg.tater.transit.model.TransitPlayerDataInfo;
import me.lucko.helper.Helper;
import me.lucko.helper.Schedulers;
import me.lucko.helper.gson.GsonProvider;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.redis.Redis;
import me.lucko.helper.terminable.TerminableConsumer;
import redis.clients.jedis.Jedis;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;

public class RedisTransitDatastore implements TransitDatastoreDao {

    private static final String HASH_NAME = "bridge-data-info";

    private Redis redis;

    @Override
    public Promise<TransitPlayerDataInfo> getPlayerDataInfo(UUID uuid) {
        return Schedulers.async().supply(() -> {
            try (Jedis jedis = redis.getJedisPool().getResource()) {
                return Optional.ofNullable(GsonProvider.standard().fromJson(jedis.hget(HASH_NAME, uuid.toString()), TransitPlayerDataInfo.class))
                        .orElse(new TransitPlayerDataInfo());
            }
        });
    }

    @Override
    public TransitDatastoreType getType() {
        return TransitDatastoreType.MONGO;
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        this.redis = Helper.serviceNullable(Redis.class);
    }
}
