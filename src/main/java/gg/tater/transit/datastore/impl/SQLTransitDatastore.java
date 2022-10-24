package gg.tater.transit.datastore.impl;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.datastore.TransitDatastoreType;
import gg.tater.transit.model.TransitPlayerDataInfo;
import me.lucko.helper.Helper;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.sql.Sql;
import me.lucko.helper.terminable.TerminableConsumer;

import javax.annotation.Nonnull;
import java.util.UUID;

public class SQLTransitDatastore implements TransitDatastoreDao {

    private Sql sql;

    @Override
    public Promise<TransitPlayerDataInfo> getPlayerDataInfo(UUID uuid) {
        return null;
    }

    @Override
    public TransitDatastoreType getType() {
        return TransitDatastoreType.SQL;
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        this.sql = Helper.serviceNullable(Sql.class);
    }
}
