package gg.tater.transit;

import gg.tater.transit.datastore.TransitDatastoreDao;
import gg.tater.transit.datastore.TransitDatastoreType;
import gg.tater.transit.datastore.impl.MongoTransitDatastore;
import gg.tater.transit.datastore.impl.RedisTransitDatastore;
import gg.tater.transit.datastore.impl.SQLTransitDatastore;
import gg.tater.transit.event.TransitEventModule;
import gg.tater.transit.model.option.TransitOption;
import gg.tater.transit.model.option.impl.TransitEnderChestOption;
import gg.tater.transit.model.option.impl.TransitFoodLevelOption;
import gg.tater.transit.model.option.impl.TransitGameModeOption;
import me.lucko.helper.Helper;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.Set;


@Plugin(
        name = "PlayerDataBridge",
        apiVersion = "1.18",
        authors = "Tater"
)
public final class TransitPlugin extends ExtendedJavaPlugin implements TransitService {

    private static final String DATASTORE_SECTION_KEY = "datastore";
    private static final String DATASTORE_TYPE_KEY = "type";

    private final Set<TransitOption> options = new HashSet<>();

    private TransitDatastoreDao dao;

    @Override
    protected void enable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigurationSection section = getConfig().getConfigurationSection(DATASTORE_SECTION_KEY);
        if (section == null) {
            getLogger().info("Your config is corrupted and does not contain the proper data. Please reset it.");
            Helper.plugins().disablePlugin(this);
            return;
        }

        TransitDatastoreType type = TransitDatastoreType.valueOf(section.getString(DATASTORE_TYPE_KEY));
        this.dao = bindModule(type == TransitDatastoreType.REDIS ? new RedisTransitDatastore() : type == TransitDatastoreType.SQL ? new SQLTransitDatastore() : new MongoTransitDatastore());
        bindModule(new TransitEventModule(getLogger(), dao));

        registerOption(new TransitGameModeOption());
        registerOption(new TransitEnderChestOption());
        registerOption(new TransitFoodLevelOption());

        options.stream()
                .filter(TransitOption::isEnabled)
                .forEach(option -> {
                    bindModule(option);
                    getLogger().info("Initialized data bridge option: " + option.getFriendly());
                });
    }

    @Override
    protected void disable() {

    }

    @Override
    public TransitDatastoreDao getDao() {
        return dao;
    }

    @Override
    public void registerOption(TransitOption option) {
        options.add(option);
        getLogger().info("Registered transit option: " + option.getFriendly());
    }
}
