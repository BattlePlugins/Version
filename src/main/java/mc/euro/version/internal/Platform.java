package mc.euro.version.internal;

import com.google.common.base.Optional;

import mc.euro.version.Tester;
import mc.euro.version.TesterFactory;
import mc.euro.version.Version;
import mc.euro.version.plugin.BukkitPlugin;
import mc.euro.version.plugin.IPlugin;
import mc.euro.version.plugin.SpongePlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.common.Sponge;

/**
 * Handles the construction of new Version objects specific to BUKKIT & SPONGE.
 *
 * @author Nikolai
 */
public abstract class Platform {

    public abstract Version<IPlugin> getPluginVersion(String pluginName);

    public abstract Version getMinecraftVersion();

    public abstract Version getApiVersion();

    public abstract Version getImplementationVersion();

    public abstract String getNmsPackage();
    
    public abstract String name();

    /**
     * What Platform is currently running ?
     *
     * @return Detects the Platform at runtime: BUKKIT, SPONGE, UNKNOWN_PLATFORM
     */
    public static Platform getPlatform() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return Platform.BUKKIT;
        } catch (ClassNotFoundException ignored) {
        }
        try {
            Class.forName("org.spongepowered.common.Sponge");
            return Platform.SPONGE;
        } catch (ClassNotFoundException ignored) {
        }
        return Platform.UNKNOWN_PLATFORM;
    }

    public static final Platform BUKKIT = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            IPlugin iplugin = new BukkitPlugin(plugin);
            Tester<IPlugin> tester = TesterFactory.getNewTester(iplugin);
            String version = (plugin == null) ? "" : plugin.getDescription().getVersion();
            return new Version<IPlugin>(version, tester);
        }

        @Override
        public Version getMinecraftVersion() {
            String version = Bukkit.getServer().getBukkitVersion();
            return new Version(version);
        }

        @Override
        public Version getApiVersion() {
            String version = Bukkit.getServer().getBukkitVersion();
            return new Version(version);
        }

        @Override
        public Version getImplementationVersion() {
            String version = Bukkit.getServer().getVersion();
            return new Version(version);
        }

        @Override
        public String getNmsPackage() {
            String NMS = null;
            try {
                // This fails for versions less than 1.4.5
                NMS = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            } catch (ArrayIndexOutOfBoundsException ex) {
                NMS = createCompatPackage();
            }
            return NMS;
        }

        private String createCompatPackage() {
            String fullVersion = Bukkit.getBukkitVersion();
            String[] parts = fullVersion.split("[-]");
            String version = parts[0];
            version = version.replace(".", "_");
            version = "v" + version;
            return version;
        }
        
        @Override
        public String name() {
            return "bukkit";
        }

    };

    public static final Platform SPONGE = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            IPlugin iplugin = new SpongePlugin(pluginName);
            Tester<IPlugin> tester = TesterFactory.getNewTester(iplugin);
            Optional<PluginContainer> container = Sponge.getGame().getPluginManager().getPlugin(pluginName);
            String version = "";
            if (container.isPresent()) {
                version = container.get().getVersion();
            }
            return new Version(version, tester);
        }

        @Override
        public Version getMinecraftVersion() {
            String minecraft = Sponge.getGame().getPlatform().getMinecraftVersion().getName();
            return new Version(minecraft);
        }

        @Override
        public Version getApiVersion() {
            String api = Sponge.getGame().getPlatform().getApiVersion();
            return new Version(api);
        }

        @Override
        public Version getImplementationVersion() {
            String platform = Sponge.getGame().getPlatform().getVersion();
            return new Version(platform);
        }

        @Override
        public String getNmsPackage() {
            return createCompatPackage();
        }
        
        private String createCompatPackage() {
            String version = getMinecraftVersion().toString();
            version = version.replace(".", "_");
            version = "v" + version;
            return version;
        }
        
        @Override
        public String name() {
            return "sponge";
        }
    };

    /**
     * We have two options here:. <br/><br/>
     * <pre>
     *     1. return empty Version objects
     *     2. throw Exceptions
     *
     * Throwing an Exception is "in-your-face": It's something that the user can't ignore.
     * It signifies that either Platform.getPlatform() has failed to properly detect
     * the currently running platform... Or it signifies that a new Platform
     * implementation needs to be added. Furthermore, we could actually be protecting
     * the user from accidentally using VersionFactory on an unsupported platform.
     *
     * I think this is better than returning empty Version objects
     * Empty Version objects would cause version checks to fail.
     * This is akin to failing silently.
     * But code execution would not be interrupted.
     * So there are pros & cons.
     * </pre>
     *
     * @author Nikolai
     */
    public static final Platform UNKNOWN_PLATFORM = new Platform() {

        @Override
        public Version<IPlugin> getPluginVersion(String pluginName) {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public Version getMinecraftVersion() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public Version getApiVersion() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public Version getImplementationVersion() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }

        @Override
        public String getNmsPackage() {
            throw new UnsupportedOperationException("Unknown Platform not supported.");
        }
        
        @Override
        public String name() {
            return "unknown";
        }
    };

}
