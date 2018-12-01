package mc.euro.version.internal;

import java.util.Optional;
import mc.euro.version.Tester;
import mc.euro.version.TesterFactory;
import mc.euro.version.Version;
import mc.euro.version.plugin.IPlugin;
import mc.euro.version.plugin.SpongePlugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.common.Sponge;

/**
 * Powers Platform.SPONGE & hides the Sponge imports from the Platform class.
 * 
 * @author Nikolai
 */
public abstract class SpongePlatform {

    public static Version<IPlugin> getPluginVersion(String pluginName) {
        IPlugin iplugin = new SpongePlugin(pluginName);
        Tester<IPlugin> tester = TesterFactory.getNewTester(iplugin);
        Optional<PluginContainer> container = Sponge.getGame().getPluginManager().getPlugin(pluginName);
        String version = "";
        if (container.isPresent()) {
            version = container.get().getVersion();
        }
        return new Version(version, tester);
    }

    public static Version getServerVersion() {
        String minecraft = Sponge.getGame().getPlatform().getMinecraftVersion().getName();
        return new Version(minecraft);
    }

    public static String getNmsPackage() {
        return createCompatPackage();
    }

    private static String createCompatPackage() {
        String version = getServerVersion().toString();
        version = version.replace(".", "_");
        version = "v" + version;
        return version;
    }

    public static Version getApiVersion() {
        String api = Sponge.getGame().getPlatform().getApi().getVersion();
        return new Version(api);
    }

    public static Version getImplementationVersion() {
        String platform = Sponge.getGame().getPlatform().getImplementation().getVersion();
        return new Version(platform);
    }

}
