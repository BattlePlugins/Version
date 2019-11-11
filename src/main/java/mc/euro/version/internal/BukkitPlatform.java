package mc.euro.version.internal;

import mc.euro.version.Tester;
import mc.euro.version.TesterFactory;
import mc.euro.version.Version;
import mc.euro.version.plugin.BukkitPlugin;
import mc.euro.version.plugin.IPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Powers Platform.BUKKIT & hides the Bukkit imports from the Platform class.
 * 
 * @author Nikolai
 */
public abstract class BukkitPlatform {

    public static Version<IPlugin> getPluginVersion(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        IPlugin iplugin = (plugin == null) ? null : new BukkitPlugin(plugin);
        Tester<IPlugin> tester = TesterFactory.getNewTester(iplugin);
        String version = (plugin == null) ? "" : plugin.getDescription().getVersion();
        return new Version<>(version, tester);
    }

    public static Version getServerVersion() {
        String version = Bukkit.getServer().getBukkitVersion();
        return new Version(version);
    }

    public static String getNmsPackage() {
        String NMS;
        try {
            // This fails for versions less than 1.4.5
            NMS = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            NMS = createCompatPackage();
        }
        return NMS;
    }

    private static String createCompatPackage() {
        String fullVersion = Bukkit.getBukkitVersion();
        String[] parts = fullVersion.split("[-]");
        String version = parts[0];
        version = version.replace(".", "_");
        version = "v" + version;
        return version;
    }

}
