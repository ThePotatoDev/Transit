package gg.tater.databridge.datastore;

import me.lucko.helper.terminable.module.TerminableModule;

public interface BridgeDatastoreDao extends TerminableModule {

    BridgeDatastoreType getType();

}
