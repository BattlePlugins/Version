package mc.euro.version.internal;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;

import mc.euro.version.Tester;
import mc.euro.version.TesterFactory;
import mc.euro.version.Version;
import mc.euro.version.plugin.IPlugin;
import mc.euro.version.plugin.NukkitPlugin;

/**
 * Powers Platform.NUKKIT & hides the Nukkit imports from the Platform class.
 *
 * @author Redned
 */
public abstract class NukkitPlatform {

    public static Version<IPlugin> getPluginVersion(String pluginName) {
        Plugin plugin = Server.getInstance().getPluginManager().getPlugin(pluginName);
        IPlugin iplugin = (plugin == null) ? null : new NukkitPlugin(plugin);
        Tester<IPlugin> tester = TesterFactory.getNewTester(iplugin);
        String version = (plugin == null) ? "" : plugin.getDescription().getVersion();
        return new Version<>(version, tester);
    }

    public static Version getServerVersion() {
        String version = Server.getInstance().getVersion();
        return new Version(version);
    }

    public static String getServerPackage() {
        return "cn.nukkit.server";
    }

    public static Version getApiVersion() {
        return new Version(Server.getInstance().getApiVersion());
    }
}
