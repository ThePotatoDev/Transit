package gg.tater.databridge;

import gg.tater.databridge.datastore.BridgeDatastoreType;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import org.bukkit.configuration.ConfigurationSection;


@Plugin(
        name = "PlayerDataBridge",
        apiVersion = "1.18",
        authors = "Tater"
)
public final class PlayerDataBridgePlugin extends ExtendedJavaPlugin {

    private static final String DATASTORE_SECTION_KEY = "datastore-info";
    private static final String DATASTORE_TYPE_KEY = "type";

    @Override
    protected void enable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigurationSection section = getConfig().getConfigurationSection(DATASTORE_SECTION_KEY);
        if (section == null) {
            section = getConfig().createSection(DATASTORE_SECTION_KEY);
            getLogger().info("Created section for datastore info, it was not present.");
        }

        BridgeDatastoreType type = BridgeDatastoreType.valueOf(section.getString("datastore-type"));
    }

    @Override
    protected void disable() {

    }
}
