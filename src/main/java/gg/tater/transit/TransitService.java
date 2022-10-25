package gg.tater.transit;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.model.option.TransitOption;

import java.util.logging.Logger;

public interface TransitService {

    TransitDatastoreDao getDao();

    Logger getLogger();

    void registerOption(TransitOption option);

}
