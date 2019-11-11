package mc.euro.version.internal;

import java.util.Optional;
import mc.euro.version.TesterFactory;
import mc.euro.version.Version;
import mc.euro.version.plugin.IPlugin;
import mc.euro.version.plugin.SpongePlugin;

import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

/**
 * Powers Platform.SPONGE & hides the Sponge imports from the Platform class.
 * 
 * @author Nikolai
 */
public abstract class SpongePlatform {

    public static Version<IPlugin> getPluginVersion(String pluginName) {
        Optional<PluginContainer> container = Sponge.getGame().getPluginManager().getPlugin(pluginName);
        IPlugin iplugin = null;
        String version = "";
        if (container.isPresent()) {
            version = container.get().getVersion().orElse("");

            iplugin = new SpongePlugin(pluginName);
        }
        return new Version<>(version, TesterFactory.getNewTester(iplugin));
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
        String api = Sponge.getGame().getPlatform().getContainer(Platform.Component.API).getVersion().orElse("");
        return new Version(api);
    }

    public static Version getImplementationVersion() {
        String platform = Sponge.getGame().getPlatform().getContainer(Platform.Component.IMPLEMENTATION).getVersion().orElse("");
        return new Version(platform);
    }

}
