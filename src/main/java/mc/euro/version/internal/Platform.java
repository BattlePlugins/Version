package mc.euro.version.internal;

import mc.euro.version.Version;
import mc.euro.version.plugin.IPlugin;

/**
 * Handles the construction of new Version objects specific to BUKKIT & SPONGE.
 *
 * @author Nikolai
 */
public abstract class Platform {

    public abstract Version<IPlugin> getPluginVersion(String pluginName);

    public abstract Version getServerVersion();

    public abstract String getNmsPackage();

    /**
     * What Platform is currently running ?
     *
     * @return Detects the Platform at runtime: BUKKIT, SPONGE, UNKNOWN_PLATFORM
     */
    public static Platform getPlatform() {
        try { // See if Bukkit/Spigot are loaded:
            Class.forName("org.bukkit.Bukkit");
            return Platform.BUKKIT;
        } catch (ClassNotFoundException ignored) {
            // Bukkit/Spigot not loaded.
        }
        try { // See if Sponge is loaded:
            Class.forName("org.spongepowered.common.Sponge");
            return Platform.SPONGE;
        } catch (ClassNotFoundException ignored) {
            // Sponge not loaded.
        }
        return Platform.UNKNOWN;
    }

    public static final Platform BUKKIT = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            return BukkitPlatform.getPluginVersion(pluginName);
        }

        @Override
        public Version getServerVersion() {
            return BukkitPlatform.getServerVersion();
        }

        @Override
        public String getNmsPackage() {
            return BukkitPlatform.getNmsPackage();
        }

    };

    public static final Platform SPONGE = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            return SpongePlatform.getPluginVersion(pluginName);
        }

        @Override
        public Version getServerVersion() {
            return SpongePlatform.getServerVersion();
        }

        @Override
        public String getNmsPackage() {
            return SpongePlatform.getNmsPackage();
        }

        public Version getApiVersion() {
            return SpongePlatform.getApiVersion();
        }

        public Version getImplementationVersion() {
            return SpongePlatform.getImplementationVersion();
        }

    };

    public static final Platform UNKNOWN = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public Version getServerVersion() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public String getNmsPackage() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

    };

}
