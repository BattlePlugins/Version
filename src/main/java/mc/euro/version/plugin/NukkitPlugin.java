package mc.euro.version.plugin;

import cn.nukkit.plugin.Plugin;

/**
 *
 * @author Redned
 */
public class NukkitPlugin implements IPlugin {

    private Plugin plugin;

    public NukkitPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() {
        return (plugin != null) && (plugin.isEnabled());
    }

}
